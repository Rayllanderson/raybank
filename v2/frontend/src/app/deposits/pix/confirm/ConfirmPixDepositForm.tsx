'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { ConfirmTransactionHeader } from '@/components/ConfirmTransactionHeader';
import { Button, Select } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useRef, useState } from 'react'
import { usePixDepositContext } from '@/context/PixDepositContext';
import { PixKey } from '@/types/Pix';
import toast from 'react-hot-toast';
import { useSession } from 'next-auth/react';
import PrimaryButton from '@/components/Buttons/PrimaryButton';

export default function ConfirmPixDepositForm({keys}: {keys: PixKey[]}) {
    const inputRef = useRef<HTMLInputElement>(null);
    const router = useRouter();
    const { pixDepositData, setCreditKey, setMessage, generateQrCode,loading } = usePixDepositContext();
    const {data} = useSession()
    const [finished, setFinished] = useState(true)

    const isDepositInvalid = useCallback(() => {
        return pixDepositData.amount === 0
    }, [pixDepositData])

    useEffect(() => {
        if (isDepositInvalid()) {
            router.push('/deposits/pix')
        }
    }, [isDepositInvalid, router]);

    useEffect(() => {
        const pixKey: PixKey = keys[0]
        setCreditKey(pixKey.key)
    },[])

    function onInputChange(event: any) {
        const value = event.target.value || null;
        if (value?.length > 140) {
            return
        }
        setMessage(value)
    }

    function onSelectChange(event: any) {
        const value = event.target.value || null;
        setCreditKey(value)
    }

    async function onButtonClick() {
        setFinished(false)
        const response = await generateQrCode()
        if (response) {
          toast.success('Pix Qr code gerado com sucesso')
          router.replace('/deposits/pix/success')
          return
        }
        setFinished(true)
    }

    return (
        <>
            {isDepositInvalid() && null}

            {!isDepositInvalid() && (
                <Container>
                    <Card>
                        <ConfirmTransactionHeader
                            title="Confira os dados do depósito"
                            amount={pixDepositData.amount}
                            beneficiaryName={data?.user?.name || 'Você mesmo'}
                        />

                        <div className="flex flex-col gap-3">
                            <InputText placeholder='Mensagem (opcional)' ref={inputRef} onChange={onInputChange} />

                            <div className='flex justify-between items-center mt-2'>
                                <p>Chave Pix</p>
                                <div className='max-w-xs w-[40%]'>
                                    <Select color='default' onChange={onSelectChange} value={pixDepositData.creditKey!}>
                                        {keys.map((key) => (
                                            <option key={key.key} value={key.key}>
                                                {key.key}
                                            </option>
                                        ))}
                                    </Select>
                                </div>
                            </div>

                            <div className='flex mt-2'>
                                <PrimaryButton disabled={loading || !finished} loading={loading || !finished} onClick={onButtonClick}>
                                    Criar código
                                </PrimaryButton>
                            </div>
                        </div>
                    </Card>
                </Container>
            )}
        </>
    );

}