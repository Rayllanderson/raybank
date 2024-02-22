'use client';

import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react'
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { AccountDepositData, useAccountDeposit } from '@/context/AccountDepositContext';
import Link from 'next/link';
import LightButton from '@/components/Buttons/LightButton';

export default function DepositAccountSuccess() {
    const { accountDepositData, resetDeposit } = useAccountDeposit();
    const [depositData, setDepositData] = useState<AccountDepositData | null>(null)
    const router = useRouter()

    useEffect(() => {
        if (!accountDepositData.success) {
            router.push('/deposits/account')
            return
        }
        setDepositData(accountDepositData)
        resetDeposit()
    }, [])

    return (
        <>
            {!depositData?.success && null}
            {depositData?.success && (
                <Container>
                    <Card>
                        <div className="">
                            <h1 className='text-lg lg:text-xl font-semibold'>Depósito realizado!</h1>
                            <h1 className='lg:text-lg'>Valor depositado: <span className='text-primary-1 font-semibold'>{MoneyFormatter.format(depositData?.amount!)}</span></h1>
                        </div>
                        <div className='flex mt-1 space-x-2 justify-end'>
                            <Link href='/deposits/account'>
                                <LightButton >
                                    Novo depósito
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

