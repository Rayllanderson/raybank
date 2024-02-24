'use client';

import Receipt from '@/components/Receipt';
import { useBoletoPayment } from '@/context/BoletoPaymentContext';
import { useSession } from 'next-auth/react';
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react'
import { getBoletoBeneficiaryName } from '../confirm/ConfirmBoletoPaymentForm';
import { BoletoDetailsResponse } from '@/types/Boleto';

export default function PaymentBoletoSuccess() {

    const { resetPayment, boleto } = useBoletoPayment()
    const router = useRouter()
    const [boletoSuccess, setBoletoSuccess] = useState<BoletoDetailsResponse | null>(null)

    const { data } = useSession()

    useEffect(() => {
        if (!boleto?.success) {
            router.replace('/payments')
            return
        }
        setBoletoSuccess(boleto)
        resetPayment()
    }, [])


    return (
        <>
            {!boletoSuccess?.success && null}
            {boletoSuccess?.success && (
                <Receipt
                    title="Pagamento realizado!"
                    amount={boletoSuccess?.boleto.value!}
                    credit={getBoletoBeneficiaryName(boletoSuccess)}
                    debit={data?.user?.name}
                    paymentType='Boleto'
                    buttonTitle='Nova pagamento'
                    buttonUrl='/payments'
                />
            )}
        </>
    )
}