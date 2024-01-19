'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { ConfirmTransactionHeader } from '@/components/ConfirmTransactionHeader';
import { Button, Select } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useRef, useState } from 'react'
import { usePixDepositContext } from '@/context/PixDepositContext';

const keys = [
    'kaguya@sama.com',
    '45b23dcf-e6c2-4da0-a1c9-ca648ff7393c'
];

export default function ConfirmPixDepositForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const router = useRouter();
    const { pixDepositData, setCreditKey, setMessage } = usePixDepositContext();

    const isDepositInvalid = useCallback(() => {
        return pixDepositData.amount === 0
    }, [pixDepositData])

    useEffect(() => {
        if (isDepositInvalid()) {
            router.push('/deposits/pix')
        }
    }, [isDepositInvalid, router]);

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

    function onButtonClick() {

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
                            beneficiaryName={'' || ''}
                        />

                        <div className="flex flex-col gap-3">
                            <InputText placeholder='Mensagem (opcional)' ref={inputRef} onChange={onInputChange} />

                            <div className='flex justify-between items-center mt-2'>
                                <p>Chave Pix</p>
                                <div className='max-w-xs w-[40%]'>
                                    <Select color='default' onChange={onSelectChange}>
                                        {keys.map((key) => (
                                            <option key={key} value={key}>
                                                {key}
                                            </option>
                                        ))}
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
            )}
        </>
    );

}