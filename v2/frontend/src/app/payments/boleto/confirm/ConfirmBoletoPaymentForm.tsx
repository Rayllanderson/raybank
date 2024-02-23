'use client';
import { Container } from '@/components/Container';
import PreviousPageButton from '@/components/PreviousPageButton';
import { Card } from '@/components/cards/Card';
import { useBoletoPayment } from '@/context/BoletoPaymentContext';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useState } from 'react'
import { ButtonConfirm } from '../../ButtonConfirm';
import { BoletoDetailsResponse } from '@/types/Boleto';
import toast from 'react-hot-toast';
import { BoletoFormatter } from '@/utils/BoletoFormatter';


export default function ConfirmBoletoPaymentForm() {
  const router = useRouter();
  const { barCode, loading, boleto: response, payBoleto } = useBoletoPayment();
  const [finished, setFinished] = useState(true)


  const isInvalidPayment = useCallback(() => {
    if (!response) return true
    return barCode === null || barCode?.toString().length < 47
  }, [barCode, response])

  useEffect(() => {
    if (isInvalidPayment()) {
      router.push('/payments/boleto')
    }
  }, [isInvalidPayment, router]);

  async function onButtonClick() {
    setFinished(false)
    const paymentResponse = await payBoleto()
    if (paymentResponse) {
      toast.success('Boleto pago com sucesso')
      router.replace('/payments/boleto/success')
      return
    }
    setFinished(true)
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
                <h1 className="text-3xl md:text-4xl font-semibold text-primary-2">{MoneyFormatter.format(response?.boleto.value!)}</h1>
                <p className='text-md md:text-lg text-gray-500 dark:text-gray-400'>para: {getBeneficiary(response!)}</p>
                <p className='text-md md:text-lg max-w-sm text-gray-500 dark:text-gray-400'>{BoletoFormatter.formatBarCode(barCode?.toString())}</p>
              </div>
            </header>

            <ButtonConfirm onClick={onButtonClick} disabled={loading || !finished}/>
          </Card>
        </Container>
      )}
    </>
  );
}

function getBeneficiary(boletoResponse: BoletoDetailsResponse) {
  if (boletoResponse.beneficiary.account) {
    return boletoResponse.beneficiary.account.name
  }
  if (boletoResponse.beneficiary.invoice) {
    return "Fatura do cartão"
  }
}
