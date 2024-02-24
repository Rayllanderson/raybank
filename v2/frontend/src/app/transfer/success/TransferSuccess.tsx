'use client';

import LightButton from '@/components/Buttons/LightButton';
import { Container } from '@/components/Container';
import Receipt from '@/components/Receipt';
import Separator from '@/components/Separator';
import { Card } from '@/components/cards/Card';
import { useTransferTransactionContext } from '@/context/TransferContext';
import { TransferTransaction } from '@/types/transactions/TransferTransaction';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { useSession } from 'next-auth/react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react'
import { FaRegMoneyBill1 } from 'react-icons/fa6';

export default function TransferSuccess() {
    const [successfulyTransaction, setSuccessfulyTransaction] = useState<TransferTransaction | null>(null)
    const { transaction, resetTransaction } = useTransferTransactionContext();
    const router = useRouter()
    const { data } = useSession()

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
                <Receipt 
                    title="Transferencia realizada!"
                    amount={successfulyTransaction?.amount!}
                    credit={successfulyTransaction?.beneficiaryName!}
                    debit={data?.user?.name}
                    paymentType={getTransferType(successfulyTransaction?.beneficiaryType!)}
                    buttonTitle='Nova Transferência'
                    buttonUrl='/transfer'
                />
            )}
        </>
    )
}

function getTransferType(beneficiaryType: 'account' | 'pix' | 'contact' | null) {
    if (beneficiaryType === 'account' || beneficiaryType === 'contact') {
        return 'Conta bancária'
    }
    if (beneficiaryType === 'pix') {
        return 'Pix'
    }
    else return ''
}