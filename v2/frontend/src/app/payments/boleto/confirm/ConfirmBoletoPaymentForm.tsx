'use client';
import { Container } from '@/app/components/Container';
import PreviousPageButton from '@/app/components/PreviousPageButton';
import { Card } from '@/app/components/cards/Card';
import { useBoletoPayment } from '@/app/context/BoletoPaymentContext';
import { MoneyFormatter } from '@/app/utils/MoneyFormatter';
import { useRouter } from 'next/navigation';
import React, { useEffect } from 'react'
import { ButtonConfirm } from '../../ButtonConfirm';


export default function ConfirmBoletoPaymentForm() {
    const router = useRouter();
    const { barCode, beneficiaryName, amount } = useBoletoPayment();

    useEffect(() => {
        if (barCode === null || barCode?.toString().length < 47) {
            router.push('/payments/boleto')
        }
    }, [barCode, router]);

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
                        <h1 className="text-3xl md:text-4xl font-semibold text-primary-2">{MoneyFormatter.format(amount)}</h1>
                        <p className='text-md md:text-lg text-gray-500 dark:text-gray-400'>para: {beneficiaryName}</p>
                        <p className='text-md md:text-lg max-w-sm text-gray-500 dark:text-gray-400'>{barCode?.toString().substring(0, 23)}</p>
                        <p className='text-md md:text-lg max-w-sm text-gray-500 dark:text-gray-400'>{barCode?.toString().substring(23)}</p>
                    </div>
                </header>

                <ButtonConfirm onClick={onButtonClick}/>
            </Card>
        </Container>
    )
}
