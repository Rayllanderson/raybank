'use client'
import DepositBoletoSuccess from '@/app/deposits/boleto/success/DepositBoletoSuccess';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { useBoletoDepositContext } from '@/context/BoletoDepositContext';
import { useInvoicePayment } from '@/context/InvoicePaymentContext';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react'

type SuccessPayment = {
    method: 'boleto' | 'account' | null
    amount: number | null
}

export default function PaymentInvoiceSuccess() {
    const { resetDeposit } = useBoletoDepositContext();
    const { setAmount, paymentMethod, amount } = useInvoicePayment();
    const [payment, setPayment] = useState<SuccessPayment | null>(null)
    const router = useRouter()
    const [checkedPayment, setCheckedPayment] = useState(false);

    useEffect(() => {
        setPayment({
            amount: amount,
            method: paymentMethod
        });
    }, []);

    useEffect(() => {
        if (payment && !checkedPayment) {
            if (paymentMethod === 'boleto') {
                resetDeposit()
            }
            setAmount(0)

            if (payment && (payment.amount === 0 || payment.amount === undefined)) {
                router.push('/payments/invoice')
            }
            setCheckedPayment(true)
        }
        
    }, [payment, paymentMethod, amount, resetDeposit, setAmount, router])
    return (
        <div>
            {(payment?.amount === 0 || payment?.amount === undefined) && null}
            {(payment?.method === 'account' && payment?.amount !== 0) &&
                <Container>
                    <Card>
                        <div>
                            <h1 className='text-lg lg:text-xl font-semibold'>Pagamento da fatura realizado!</h1>
                            <h1 className='lg:text-lg mt-2'>Valor pago utilizando saldo da conta: <span className='text-primary-1 font-semibold'>{MoneyFormatter.format(payment?.amount!)}</span></h1>
                            <p className='font-thin'>Em alguns minutos o valor será creditado em sua fatura</p>
                        </div>
                    </Card>
                </Container>}
            <div>{payment?.method === 'boleto' && <DepositBoletoSuccess />}</div>
        </div>
    )
}
