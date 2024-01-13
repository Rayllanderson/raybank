'use client';
import { Container } from '@/app/components/Container';
import PreviousPageButton from '@/app/components/PreviousPageButton';
import { Card } from '@/app/components/cards/Card';
import { usePixPayment } from '@/app/context/PixPaymentContext';
import { MoneyFormatter } from '@/app/utils/MoneyFormatter';
import { Button } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useEffect } from 'react'


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
                <header className="flex flex-col gap-3">
                    <div>
                        <PreviousPageButton />
                    </div>
                    <div className="text-start">
                        <h1 className="text-lg md:text-xl lg:text-2xl font-semibold">Pagando</h1>
                        <h1 className="text-2xl md:text-3xl lg:text-4xl font-semibold text-primary-2">{MoneyFormatter.format(amount)}</h1>
                        <p className='text-md md:text-lg text-gray-500 dark:text-gray-400'>para: {beneficiaryName}</p>
                    </div>
                </header>

                <div className="mt-2 flex flex-col gap-3">
                    <div className='flex mt-2'>
                        <Button color='primary' className={`w-full`}>
                            <p>Confirmar Pagamento</p>
                        </Button>
                    </div>
                </div>
            </Card>
        </Container>
    )
}