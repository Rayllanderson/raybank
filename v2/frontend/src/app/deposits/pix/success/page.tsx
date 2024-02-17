'use client'
import { useAccountDeposit } from '@/context/AccountDepositContext';
import { useBoletoDepositContext } from '@/context/BoletoDepositContext';
import { usePixDepositContext } from '@/context/PixDepositContext';
import React, { useEffect } from 'react'

export default function page() {
    const { setAmount, qrCode, pixDepositData } = usePixDepositContext();

    useEffect(() => {
        setAmount(0)
    }, [])
    return (
        <div>Sucesso!
            <div>{qrCode}</div>
        </div>

    )
}
