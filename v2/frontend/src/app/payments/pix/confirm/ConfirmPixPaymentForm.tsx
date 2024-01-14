'use client';
import { Container } from '@/app/components/Container';
import { Card } from '@/app/components/cards/Card';
import { usePixPayment } from '@/app/context/PixPaymentContext';
import { useRouter } from 'next/navigation';
import React, { useEffect } from 'react'
import { ButtonConfirm } from '../../ButtonConfirm';
import { ConfirmTransactionHeader } from '../../../components/ConfirmTransactionHeader';


export default function ConfirmPixPaymentForm() {
    const router = useRouter();
    const { qrCode, beneficiaryName, amount } = usePixPayment();

    useEffect(() => {
        if (qrCode === null || qrCode.length < 140) {
            router.push('/payments/pix')
        }
    }, [qrCode, router]);

    function onInputChange(event: any) {
        const value = event.target.value || null;
        if (value?.length > 140) {
            return
        }
    }

    function onButtonClick() {

    }

    return (
        <Container>
            <Card >
                <ConfirmTransactionHeader title="Pagando" amount={amount} beneficiaryName={beneficiaryName}/>

                <ButtonConfirm onClick={onButtonClick}/>
            </Card>
        </Container>
    )
}