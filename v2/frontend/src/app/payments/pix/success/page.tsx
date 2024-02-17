'use client'
import { useBoletoDepositContext } from '@/context/BoletoDepositContext';
import { useBoletoPayment } from '@/context/BoletoPaymentContext';
import { useInvoicePayment } from '@/context/InvoicePaymentContext';
import { usePixPayment } from '@/context/PixPaymentContext';
import React, { useEffect } from 'react'

export default function page() {
    const { setQrCode } = usePixPayment()
    useEffect(() => {
        setQrCode('')
    }, [])

    return (
        <div>Sucesso!
        </div>

    )
}
