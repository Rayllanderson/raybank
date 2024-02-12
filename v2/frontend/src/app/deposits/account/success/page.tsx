'use client'
import { useAccountDeposit } from '@/context/AccountDepositContext';
import React, { useEffect } from 'react'

export default function page() {
    const { setAmount } = useAccountDeposit();

    useEffect(() => {
        setAmount(0)
    }, [])
    return (
        <div>Sucesso!</div>
    )
}
