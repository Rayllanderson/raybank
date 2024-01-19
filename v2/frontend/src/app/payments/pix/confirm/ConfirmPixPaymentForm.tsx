'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { usePixPayment } from '@/context/PixPaymentContext';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect } from 'react'
import { ButtonConfirm } from '../../ButtonConfirm';
import { ConfirmTransactionHeader } from '../../../../components/ConfirmTransactionHeader';


export default function ConfirmPixPaymentForm() {
    const router = useRouter();
    const { qrCode, beneficiaryName, amount, description } = usePixPayment();

    const isInvalidPayment = useCallback(() => {
        return qrCode === null || qrCode.length < 140
    }, [qrCode])

    useEffect(() => {
        if (isInvalidPayment()) {
            router.push('/payments/pix')
        }
    }, [isInvalidPayment, router]);

    function onButtonClick() {

    }

    return (
        <>
            {isInvalidPayment() && null}
            {!isInvalidPayment() && (
                <Container>
                    <Card >
                        <ConfirmTransactionHeader title="Pagando" amount={amount} beneficiaryName={beneficiaryName} />
                        {
                            description && (
                                <div className='text-start max-w-xs'>
                                    <p className="md:text-lg">Mensagem</p>
                                    <p className="md:text-lg text-gray-500 dark:text-gray-400">{description}</p>
                                </div>
                            )
                        }
                        <ButtonConfirm onClick={onButtonClick} />
                    </Card>
                </Container>
            )}
        </>
    )
}
