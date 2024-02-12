'use client'

import { useTransferTransactionContext } from '@/context/TransferContext'
import { TransferTransaction } from '@/types/transactions/TransferTransaction';
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react'

export default function page() {
    const [successfulyTransaction, setSuccessfulyTransaction] = useState<TransferTransaction | null>(null)
    const { transaction, resetTransaction } = useTransferTransactionContext();
    const router = useRouter()

    useEffect(() => {
        if (!transaction.success) {
            router.replace('/transfer')
            return
        }
        setSuccessfulyTransaction(transaction)
        resetTransaction()
    }, [])

    return (
        <>
            {!successfulyTransaction?.success && null}
            {successfulyTransaction?.success && (
                <div>sucesso!</div>
            )}
        </>
    )
}
