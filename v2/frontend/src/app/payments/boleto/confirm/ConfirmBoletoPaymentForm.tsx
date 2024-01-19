'use client';
import { Container } from '@/components/Container';
import PreviousPageButton from '@/components/PreviousPageButton';
import { Card } from '@/components/cards/Card';
import { useBoletoPayment } from '@/context/BoletoPaymentContext';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect } from 'react'
import { ButtonConfirm } from '../../ButtonConfirm';


export default function ConfirmBoletoPaymentForm() {
    const router = useRouter();
    const { barCode, beneficiaryName, amount } = useBoletoPayment();

    const isInvalidPayment = useCallback(() => {
        return barCode === null || barCode?.toString().length < 47
    }, [barCode])

    useEffect(() => {
        if (isInvalidPayment()) {
            router.push('/payments/boleto')
        }
    }, [isInvalidPayment, router]);

    function onButtonClick() {

    }

    return (
        <>
          {isInvalidPayment() && null}
      
          {!isInvalidPayment() && (
            <Container>
              <Card>
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
          )}
        </>
      );
      
}
