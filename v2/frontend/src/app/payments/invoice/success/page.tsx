'use client'
import { useBoletoDepositContext } from '@/context/BoletoDepositContext';
import { useInvoicePayment } from '@/context/CardPaymentContext';
import React, { useEffect } from 'react'

export default function page() {
    const { setAmount, boletoDepositData } = useBoletoDepositContext();
    const { setAmount: setInvoiceAmount, paymentMethod } = useInvoicePayment();

    useEffect(() => {
        setAmount(0)
        setInvoiceAmount(0)
    }, [])

    return (
        <div>Sucesso!
            <div>{paymentMethod === 'boleto' && boletoDepositData.barCode}</div>
        </div>

    )
}
