'use client';

import Receipt from '@/components/Receipt';
import { usePixPayment } from '@/context/PixPaymentContext';
import { QrCode } from '@/types/Pix';
import { useSession } from 'next-auth/react';
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react'

export default function PaymentPixSuccess() {

    const { resetPayment, pix } = usePixPayment()
    const router = useRouter()
    const [pixSuccess, setPixSuccess] = useState<QrCode | null>(null)

    const { data } = useSession()

    useEffect(() => {
        if (!pix?.success) {
            router.replace('/payments')
            return
        }
        setPixSuccess(pix)
        resetPayment()
    }, [])


    return (
        <>
            {!pixSuccess?.success && null}
            {pixSuccess?.success && (
                <Receipt
                    title="Pagamento realizado!"
                    amount={pixSuccess?.amount!}
                    credit={pixSuccess?.creditName!}
                    debit={data?.user?.name}
                    paymentType='Pix'
                    buttonTitle='Nova pagamento'
                    buttonUrl='/payments'
                />
            )}
        </>
    )
}