'use client';

import LightButton from '@/components/Buttons/LightButton';
import PrimaryButton from '@/components/Buttons/PrimaryButton';
import { Container } from '@/components/Container';
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
                <Container>
                    <Card>
                        <h1 className='text-lg lg:text-xl font-semibold'>Transferencia realizada!</h1>
                        <ul>
                            <ListItem keyName='Valor' value={MoneyFormatter.format(successfulyTransaction?.amount!)} />
                            <ListItem keyName='Tipo de transferência' value={getTransferType(successfulyTransaction?.beneficiaryType!)} />
                        </ul>

                        <ListHeader value='Destino' />
                        <ul>
                            <ListItem keyName='Nome' value={successfulyTransaction?.beneficiaryName!} />
                        </ul>

                        <ListHeader value='Origem' />
                        <ul>
                            <ListItem keyName='Nome' value={data?.user?.name} />
                        </ul>
                        <div className='flex mt-5 space-x-2 justify-end'>
                            <Link href='/transfer'>
                                <LightButton >
                                    Nova Transferência
                                </LightButton>
                            </Link>
                            <Link href='/'>
                                <LightButton >
                                    Voltar para Home
                                </LightButton>
                            </Link>
                        </div>
                    </Card>
                </Container>
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

function ListHeader({ value }: { value: 'Origem' | 'Destino' }) {
    return (
        <>
            <div className={`mt-2 mb-[-5px] flex items-center space-x-1 ${value === 'Destino' ? 'text-green-500' : `text-red-500`}`}>
                <div>
                    <FaRegMoneyBill1 className='w-5 h-5' />
                </div>
                <p className='text-lg font-semibold'>{value}</p>
            </div>
            <Separator className='mt-0' />
        </>)
}

function ListItem({ keyName, value }: { keyName: string, value: string }) {
    return <li className='flex justify-between'>
        <div className='font-semibold'>{keyName}</div>
        <div>{value}</div>
    </li>
}