'use client'
import { useBoletoDepositContext } from '@/context/BoletoDepositContext';
import React, { useEffect } from 'react'

export default function page() {
    const { setAmount, boletoDepositData } = useBoletoDepositContext();

    useEffect(() => {
        setAmount(0)
    }, [])
    return (
        <div>Sucesso!
            <div>{boletoDepositData.barCode}</div>
        </div>

    )
}
