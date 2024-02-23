'use client';
import { Container } from '@/components/Container';
import PreviousPageButton from '@/components/PreviousPageButton';
import { Card } from '@/components/cards/Card';
import { useBoletoDepositContext } from '@/context/BoletoDepositContext';
import { useInvoicePayment } from '@/context/InvoicePaymentContext';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useState } from 'react'
import toast from 'react-hot-toast';
import { FaAngleRight, FaBarcode, FaMoneyBill } from 'react-icons/fa6';

export default function CardPaymentForm() {
  const router = useRouter();
  const { amount, setPaymentMethod, invoice, payCurrent, loading: accountLoading } = useInvoicePayment();
  const { setAmount: setBoletoAmount, generateBoleto, loading: boletoLoading } = useBoletoDepositContext();
  const [finished, setFinished] = useState(true)


  const isInvalidPayment = useCallback(() => {
    return amount === 0
  }, [amount])


  useEffect(() => {
    setBoletoAmount(amount)
  }, []);

  useEffect(() => {
    if (isInvalidPayment()) {
      router.push('/payments/invoice')
    }
  }, [isInvalidPayment, router]);


  const setPaymentMethodOnClick = async (paymentMethod: 'account' | 'boleto') => {
    setPaymentMethod(paymentMethod)
    if (paymentMethod === 'boleto') {
      await payUsingBoleto()
    }
    else if (paymentMethod === 'account') {
      await payUsingAccount()
    }
  }

  async function payUsingAccount() {
    setFinished(false)
    const response = await payCurrent()
    if (response) {
      toast.success('Fatura paga com sucesso')
      router.replace('/payments/invoice/success')
    }
    setFinished(true)
  }

  async function payUsingBoleto() {
    setFinished(false)
    const response = await generateBoleto(invoice?.id!, 'invoice')
    if (response) {
      toast.success('Boleto gerado com sucesso')
      router.replace('/payments/invoice/success')
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
                <h1 className="text-lg md:text-xl lg:text-2xl font-semibold">Você está pagando</h1>
                <h1 className="text-2xl md:text-3xl lg:text-4xl font-semibold text-primary-2">{MoneyFormatter.format(amount)}</h1>
                <p className='text-md md:text-lg text-gray-500 dark:text-gray-400'>Total da fatura aberta: {MoneyFormatter.format(invoice?.total!)}</p>
              </div>
            </header>
            <div className='flex flex-col justify-between items-center mt-2 space-y-2'>
              <InvoicePaymentMethod title="Pague com saldo da conta" method='account' onClick={setPaymentMethodOnClick} icon={FaMoneyBill} disabled={boletoLoading || accountLoading || !finished} />
              <InvoicePaymentMethod title="Gerar um boleto" method='boleto' onClick={setPaymentMethodOnClick} icon={FaBarcode} disabled={boletoLoading || !finished || accountLoading} />
            </div>
          </Card>
        </Container>
      )}
    </>
  );

}

function InvoicePaymentMethod({ title, method, icon: Icon, onClick }: { disabled: boolean, title: string, icon: any, method: 'account' | 'boleto', onClick: (v: 'account' | 'boleto') => void; }) {
  return (
    <button onClick={() => onClick(method)} className='w-full hover:scale-[1.03] transition-transform'>
      <div className='flex justify-between items-center'>
        <div className="flex justify-between items-center space-x-3">
          <div>
            <Icon className='h-6 w-6 text-gray-800 dark:text-gray-300' />
          </div>
          <div>
            <p className='text-lg'>{title}</p>
          </div>
        </div>
        <div>
          <FaAngleRight />
        </div>
      </div>
    </button>
  )
}