'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { useTransferTransactionContext } from '@/context/TransferContext';
import { ConfirmTransactionHeader } from '@/components/ConfirmTransactionHeader';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useRef, useState } from 'react'
import toast from 'react-hot-toast';
import PrimaryButton from '@/components/Buttons/PrimaryButton';


export default function ConfirmTransferForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const router = useRouter();
    const { transaction, setMessage, transfer, loading } = useTransferTransactionContext();
    const [finished, setFinished] = useState(true)

    const isInvalidTransaction = useCallback(() => {
        return transaction.amount === 0 || transaction.beneficiary === null
    }, [transaction])

    useEffect(() => {
        if (isInvalidTransaction()) {
            router.push('/transfer')
        }
    }, [isInvalidTransaction, router]);

    function onInputChange(event: any) {
        const value = event.target.value || null;
        if (value?.length > 140) {
            return
        }
        setMessage(value)
    }

    async function onButtonClick() {
        setFinished(false)
        const response = await transfer()
        if (response) {
            router.push('/transfer/success')
            toast.success('Transferência realizada com sucesso')
            return
        }
        setFinished(true)
    }

    return (
        <>
            {isInvalidTransaction() && null}
            {!isInvalidTransaction() &&
                <Container>
                    <Card >
                        <ConfirmTransactionHeader title="Transferindo" amount={transaction.amount} beneficiaryName={transaction.beneficiaryName || ''} />

                        <div className="mt-2 flex flex-col gap-3">
                            <InputText placeholder='Mensagem (opcional)' ref={inputRef} onChange={onInputChange} />

                            <div className='flex mt-2'>
                                <PrimaryButton disabled={loading || !finished} loading={loading || !finished} className={`w-full`} onClick={onButtonClick}>
                                    <p>Confirmar Transferência</p>
                                </PrimaryButton>
                            </div>
                        </div>
                    </Card>
                </Container>
            }
        </>
    )
}