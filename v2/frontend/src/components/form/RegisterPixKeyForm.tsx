import { pixTypeTranslationsStrings } from '@/utils/PixKeyUtil'
import { zodResolver } from '@hookform/resolvers/zod'
import { Label, Select } from 'flowbite-react'
import React, { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form'
import { z } from 'zod'
import InputNumeric from '../inputs/InputNumeric'
import InputText from '../inputs/InputText'
import Separator from '../Separator'
import PrimaryButton from '../Buttons/PrimaryButton'
import { RegexPattern } from '@/utils/PixRegexPattern'
import { PixService } from '@/services/PixService'
import toast from 'react-hot-toast'
import { PixType } from '@/types/Pix'
import { usePixRegisterKey } from '@/context/PixRegisterKeyContext'
import { useRouter } from 'next/navigation';

export const registerPixKeySchema = z.object({
    type: z.nativeEnum(PixType),
    key: z.string()
}).refine((data) => {
    if (data.type === 'RANDOM') {
        return true
    }
    return (
        data.type === PixType.PHONE && RegexPattern.PHONE_REGEX.test(data.key) ||
        data.type === PixType.EMAIL && RegexPattern.EMAIL_REGEX.test(data.key) ||
        data.type === PixType.CPF && RegexPattern.CPF_REGEX.test(data.key)
    );
});

export type RegisterPixKeySchemaData = z.infer<typeof registerPixKeySchema>;

export default function RegisterPixKeyForm({ closeModal }: { closeModal: () => void }) {
    const router = useRouter()
    const { register, watch, handleSubmit, formState } = useForm<RegisterPixKeySchemaData>({
        resolver: zodResolver(registerPixKeySchema)
    })
    const { loading, registerKey } = usePixRegisterKey()

    const keyType = watch('type') === undefined ? Object.keys(pixTypeTranslationsStrings)[0] : watch('type')
    const keyValue = watch('key')
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    useEffect(() => {
        if (keyType === 'PHONE') {
            setIsButtonDisabled(!(RegexPattern.PHONE_REGEX.test(keyValue)))
        }

        if (keyType === 'EMAIL') {
            setIsButtonDisabled(!(RegexPattern.EMAIL_REGEX.test(keyValue)))
        }

        if (keyType === 'CPF') {
            setIsButtonDisabled(!(RegexPattern.CPF_REGEX.test(keyValue)))
        }
    }, [keyType, keyValue])


    useEffect(() => {
        setIsButtonDisabled(true)
        if (keyType === 'RANDOM') {
            setIsButtonDisabled(false)
        }
    }, [keyType])

    const onSubmit = async (data: RegisterPixKeySchemaData) => {
        const response = await registerKey(data)
        if (response) {
            toast.success(`Chave ${response?.key} registrada com sucesso`)
            closeModal()
            router.refresh()
        }
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <div className='w-full space-y-2' >
                <div>
                    <Label className='text-base'>Tipo de chave que quer registrar?</Label>

                    <Select required color='default'
                        {...register('type')}>

                        {Object.keys(pixTypeTranslationsStrings).map((pixType) => {
                            return <option key={pixType} value={pixType} >
                                {pixTypeTranslationsStrings[pixType]}
                            </option>
                        })}
                    </Select>

                </div>

                <div>
                    {keyType !== 'RANDOM' && <Label className='text-base'>Digite a chave</Label>}
                    {keyType === 'PHONE' && <InputNumeric placeholder='Numero do telefone' sizing='sm' {...register('key')} />}
                    {keyType === 'CPF' && <InputNumeric placeholder='Digite o CPF'{...register('key')} sizing='sm' />}
                    {keyType === 'EMAIL' && <InputText placeholder='Digite o Email' {...register('key')} sizing='sm' />}
                </div>
            </div>

            {formState.errors.key && <span className='text-red-500'>Chave incorreta para Tipo de chave escolhido</span>}
            {formState.errors.type && <span className='text-red-500'>Tipo de chave inválido</span>}

            <Separator />

            <div className='flex justify-end space-x-2 mt-2'>
                <PrimaryButton type='submit' loading={loading} disabled={isButtonDisabled || loading}>Registrar</PrimaryButton>
            </div>
        </form>
    )
}
