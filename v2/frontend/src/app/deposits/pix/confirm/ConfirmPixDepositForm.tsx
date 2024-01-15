'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { ConfirmTransactionHeader } from '@/components/ConfirmTransactionHeader';
import { Button, Select } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useEffect, useRef, useState } from 'react'
import { usePixDepositContext } from '@/context/PixDepositContext';


export default function ConfirmPixDepositForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const router = useRouter();
    const { pixDepositData, setCreditKey, setMessage } = usePixDepositContext();

    useEffect(() => {
        if (pixDepositData.amount === 0) {
            router.push('/deposit/pix')
        }
    }, [pixDepositData, router]);

    function onInputChange(event: any) {
        const value = event.target.value || null;
        if (value?.length > 140) {
            return
        }
        setMessage(value)
    }

    function onButtonClick() {

    }

    return (
        <Container>
            <Card >
                <ConfirmTransactionHeader title="Confira os dados do depósito" amount={pixDepositData.amount} beneficiaryName={'' || ''} />

                <div className="flex flex-col gap-3">
                    <InputText placeholder='Mensagem (opcional)' ref={inputRef} onChange={onInputChange} />

                    <div className='flex justify-between items-center mt-2'>
                        <p>Chave Pix</p>
                        <div className='max-w-xs w-[40%]'>
                            <Select color='default'>
                                <option>kaguya@sama.com</option>
                                <option>45b23dcf-e6c2-4da0-a1c9-ca648ff7393c</option>
                            </Select>
                        </div>
                    </div>

                    <div className='flex mt-2'>
                        <Button color='primary' className={`w-full`}>
                            <p>Criar código</p>
                        </Button>
                    </div>
                </div>
            </Card>
        </Container>
    )
}