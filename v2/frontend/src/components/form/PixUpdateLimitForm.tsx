import React, { useEffect, useRef, useState } from 'react'
import { CurrencyInput } from '../inputs/InputMoney'
import { MoneyFormatter } from '@/utils/MoneyFormatter'
import { useRouter } from 'next/navigation'
import Separator from '../Separator'
import PrimaryButton from '../Buttons/PrimaryButton'
import { usePixUpdateLimit } from '@/context/PixLimitContext'
import toast from 'react-hot-toast'
import LoadingDiv, { loadingDiv, loadingDivDefault } from '../LoadingDiv'


export default function PixUpdateLimitForm({ closeModal }: { closeModal: () => void }) {
    const [currentLimit, setCurrentLimit] = useState<number | null>(null);
    const { findLimit } = usePixUpdateLimit()
    const inputRef = useRef<HTMLInputElement>(null);

    const { loading, findLimitLoading, updateLimit } = usePixUpdateLimit()
    const [limit, setLimit] = useState(currentLimit ?? 0);
    const [error, setError] = useState<string | null>(null);
    const router = useRouter()

    useEffect(() => {
        const fetchLimit = async () => {
            const response = await findLimit()
            setCurrentLimit(response?.limit ?? null)
            setLimit(response?.limit ?? 0)
        }
        fetchLimit()
    }, [])


    function onInputChange(value: any) {
        setError(null)
        const inputNumber = parseFloat(value || '0');
        setLimit(inputNumber)
    }

    useEffect(() => {
        if (currentLimit !== null) {
            setLimit(currentLimit);
            if (inputRef.current) {
                inputRef.current.value = MoneyFormatter.format(currentLimit); 
            }
        }
    }, [currentLimit]);


    const submit = async () => {
        if (limit < 0) {
            setError('Limite precisa ser positivo')
            return
        }
        const response = await updateLimit(limit!)
        if (response) {
            toast.success(`Limite ajustado para ${MoneyFormatter.format(response?.newLimit)}`)
            closeModal()
            router.refresh()
        }
    }

    return (
        <>
            <div className='w-full'>
                <CurrencyInput value={limit} onValueChange={onInputChange} required ref={inputRef}/>
            </div>
            <div>
                <div className='text-sm flex items-center'> Limite atual: {findLimitLoading ? (
                    <div className='ml-3 w-[30%]'>
                        <LoadingDiv className='bg-gray-300 dark:bg-black-3 rounded-md' />
                    </div>
                )
                    : (
                        currentLimit === null && !findLimitLoading ?
                            'Erro ao buscar limite' :
                            MoneyFormatter.format(currentLimit!)
                    )}
                </div>
            </div>

            {error && <span className='text-red-500'>{error}</span>}
            <Separator />

            <PrimaryButton type='submit' className='mt-1' onClick={submit} loading={loading} disabled={loading}>
                Ajustar
            </PrimaryButton>
        </>
    )
}
