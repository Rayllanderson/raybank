'use client';
import { Container } from '@/app/components/Container';
import PreviousPageButton from '@/app/components/PreviousPageButton';
import { Card } from '@/app/components/cards/Card';
import InputText from '@/app/components/inputs/InputText';
import { useTransferTransactionContext } from '@/app/context/TransferContext';
import { ConfirmTransactionHeader } from '@/app/components/ConfirmTransactionHeader';
import { MoneyFormatter } from '@/app/utils/MoneyFormatter';
import { Button } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useEffect, useRef, useState } from 'react'


export default function ContactForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const router = useRouter();
    const { transaction, setMessage } = useTransferTransactionContext();

    useEffect(() => {
        if (transaction.amount === 0 || transaction.beneficiary === null) {
            router.push('/accounts/transfer')
        }
    }, [transaction, router]);

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
                <ConfirmTransactionHeader title="Transferindo" amount={transaction.amount} beneficiaryName={transaction.beneficiaryName || ''} />

                <div className="mt-2 flex flex-col gap-3">
                    <InputText placeholder='Mensagem (opcional)' ref={inputRef} onChange={onInputChange} />

                    <div className='flex mt-2'>
                        <Button color='primary' className={`w-full`}>
                            <p>Confirmar Transferência</p>
                        </Button>
                    </div>
                </div>
            </Card>
        </Container>
    )
}