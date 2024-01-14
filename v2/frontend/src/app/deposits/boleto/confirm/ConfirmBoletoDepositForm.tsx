'use client';
import { Container } from '@/app/components/Container';
import { Card } from '@/app/components/cards/Card';
import { ConfirmTransactionHeader } from '@/app/components/ConfirmTransactionHeader';
import { Button } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useEffect } from 'react'
import { useBoletoDepositContext } from '@/app/context/BoletoDepositContext';


export default function ConfirmBoletoDepositForm() {
    const router = useRouter();
    const { boletoDepositData, setBeneficiaryId, setBeneficiaryType } = useBoletoDepositContext();

    useEffect(() => {
        if (boletoDepositData.amount === 0) {
            router.push('/deposit/boleto')
        }
    }, [boletoDepositData, router]);

    function onButtonClick() {

    }

    return (
        <Container>
            <Card >
                <ConfirmTransactionHeader title="Confira os dados do depósito" amount={boletoDepositData.amount} beneficiaryName={boletoDepositData.beneficiaryName || ''} />

                <div className="flex flex-col gap-3">

                    <div className='flex mt-2'>
                        <Button color='primary' className={`w-full`}>
                            <p>Gerar boleto</p>
                        </Button>
                    </div>
                </div>
            </Card>
        </Container>
    )
}