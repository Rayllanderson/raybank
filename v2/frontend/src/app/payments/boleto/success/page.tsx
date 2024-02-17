'use client'
import { useBoletoDepositContext } from '@/context/BoletoDepositContext';
import { useBoletoPayment } from '@/context/BoletoPaymentContext';
import { useInvoicePayment } from '@/context/InvoicePaymentContext';
import React, { useEffect } from 'react'

export default function page() {
    const { setBarCode } = useBoletoPayment();

    useEffect(() => {
        setBarCode(0)
    }, [])

    return (
        <div>Sucesso!
        </div>

    )
}
