'use client';

import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { useRouter } from 'next/navigation';
import React, { useEffect, useState } from 'react'
import ClipboardCopy from '../../../../components/ClipboardCopy';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { BoletoDepositData, useBoletoDepositContext } from '@/context/BoletoDepositContext';
import { FaBarcode } from 'react-icons/fa6';
import { BoletoFormatter } from '@/utils/BoletoFormatter';

export default function DepositBoletoSuccess() {
    const { boletoDepositData, resetDeposit } = useBoletoDepositContext();
    const [depositData, setDepositData] = useState<BoletoDepositData | null>(null)
    const router = useRouter()

    useEffect(() => {
        if (!boletoDepositData.success) {
            router.push('/deposits/boleto')
            return
        }
        setDepositData(boletoDepositData)
        resetDeposit()
    }, [])

    return (
        <>
            {!depositData?.success && null}
            {depositData?.success && (
                <Container size="max-w-md md:max-w-lg lg:max-w-xl">
                    <Card className='w-full'>
                        <div className="">
                            <h1 className='text-lg lg:text-xl font-semibold'>Boleto Gerado!</h1>
                            <h1 className='lg:text-lg'>Valor a ser pago <span className='text-primary-1 font-semibold'>{MoneyFormatter.format(depositData?.amount!)}</span></h1>
                        </div>

                        <div className='flex justify-center'>
                            <FaBarcode className='w-56 h-24' />
                        </div>

                        <div className='flex justify-center'>
                            <div className='max-w-lg'>
                                <p className='text-wrap break-all text-primary-3 font-semibold'>{BoletoFormatter.formatBarCode(depositData?.barCode!)}</p>
                            </div>
                        </div>
                        <ClipboardCopy text={depositData?.barCode!} />
                    </Card>
                </Container>
            )}
        </>
    )
}

