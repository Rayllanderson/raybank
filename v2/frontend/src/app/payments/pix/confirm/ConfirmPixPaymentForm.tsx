'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { usePixPayment } from '@/context/PixPaymentContext';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useState } from 'react'
import { ButtonConfirm } from '../../ButtonConfirm';
import { ConfirmTransactionHeader } from '../../../../components/ConfirmTransactionHeader';
import toast from 'react-hot-toast';


export default function ConfirmPixPaymentForm() {
    const router = useRouter();
    const { qrCode, payPixQrCode, pix } = usePixPayment();
    const [finished, setFinished] = useState(true)

    const isInvalidPayment = useCallback(() => {
        return pix === null || qrCode.length < 100
    }, [pix, qrCode])

    useEffect(() => {
        if (isInvalidPayment()) {
            router.push('/payments/pix')
        }
    }, [isInvalidPayment, router]);

    async function onButtonClick() {
        setFinished(false)
        const paymentResponse = await payPixQrCode()
        if (paymentResponse) {
            toast.success('Pix pago com sucesso')
            router.replace('/payments/pix/success')
            return
        }
        setFinished(true)
    }

    return (
        <>
            {isInvalidPayment() && null}
            {!isInvalidPayment() && (
                <Container>
                    <Card >
                        <ConfirmTransactionHeader title="Pagando" amount={pix?.amount!} beneficiaryName={pix?.creditName!} />
                        {
                            pix?.description && (
                                <div className='text-start max-w-lg'>
                                    <label htmlFor="description" className="md:text-lg">Mensagem</label>
                                    <input id='description' disabled className="md:text-lg p-2 rounded-md w-full bg-gray-200 text-gray-500 dark:bg-black-3" value={pix?.description} />
                                </div>
                            )
                        }
                        <ButtonConfirm onClick={onButtonClick} disabled={!finished}/>
                    </Card>
                </Container>
            )}
        </>
    )
}
