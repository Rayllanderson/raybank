'use client'
import PrimaryButton from '@/components/Buttons/PrimaryButton'
import { useBoletoPayment } from '@/context/BoletoPaymentContext'
import { BoletoDetailsResponse } from '@/types/Boleto'
import { useRouter } from 'next/navigation'
import React from 'react'

export default function PayBoletoButton({boletoData}: {boletoData: BoletoDetailsResponse}) {

    const { setAmount, setBarCode, setBeneficiaryName } = useBoletoPayment()
    const router = useRouter()

    const redirectToBoletoPayment = () => {
        setAmount(boletoData.boleto.value)
        setBarCode(boletoData.boleto.barCode.replace(/[^\d]/g, ''))
        setBeneficiaryName(boletoData.boleto.title)
        router.push('/payments/boleto/confirm')
    }

    return (
        <PrimaryButton onClick={redirectToBoletoPayment}>Pagar</PrimaryButton>
    )
}
