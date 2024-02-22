'use client';

import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { PixDepositData, usePixDepositContext } from '@/context/PixDepositContext';
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react'
import ClipboardCopy from './ClipboardCopy';
import { BsQrCode } from "react-icons/bs";
import { MoneyFormatter } from '@/utils/MoneyFormatter';

export default function DepositPixSuccess() {
    const { qrCode, resetDeposit, pixDepositData } = usePixDepositContext();
    const [depositData, setDepositData] = useState<PixDepositData | null>(null)
    const router = useRouter()

    useEffect(() => {
        if (!pixDepositData.success) {
            router.push(' /deposits/pix')
            return
        }
        setDepositData(pixDepositData)
        resetDeposit()
    }, [])

    return (
        <>
            {!depositData?.success && null}
            {depositData?.success && (
                <Container>
                    <Card>
                        <div className="">
                            <h1 className='text-lg lg:text-xl font-semibold'>Qr Code Gerado!</h1>
                            <h1 className='lg:text-lg'>Valor a ser pago <span className='text-primary-1 font-semibold'>{MoneyFormatter.format(depositData?.amount!)}</span></h1>
                        </div>

                        <div className='flex justify-center'>
                            <BsQrCode className='w-40 h-40' />
                        </div>

                        <div className='flex justify-center'>
                            <div className='max-w-md'>
                                <p className='text-wrap break-all text-primary-3 font-semibold'>{qrCode}</p>
                            </div>
                        </div>
                        <ClipboardCopy text={qrCode!} />
                    </Card>
                </Container>
            )}
        </>
    )
}

