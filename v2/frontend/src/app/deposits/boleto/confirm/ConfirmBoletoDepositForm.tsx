'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { ConfirmTransactionHeader } from '@/components/ConfirmTransactionHeader';
import { Button } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect } from 'react'
import { useBoletoDepositContext } from '@/context/BoletoDepositContext';


export default function ConfirmBoletoDepositForm() {
    const router = useRouter();
    const { boletoDepositData, setBeneficiaryId, setBeneficiaryType } = useBoletoDepositContext();

    const isDepositInvalid = useCallback(() => {
        return boletoDepositData.amount === 0
    }, [boletoDepositData])

    useEffect(() => {
        if (isDepositInvalid()) {
            router.push('/deposits/boleto')
        }
    }, [isDepositInvalid, router]);

    function onButtonClick() {

    }

    return (
        <>
          {isDepositInvalid() && null}
      
          {!isDepositInvalid() && (
            <Container>
              <Card>
                <ConfirmTransactionHeader
                  title="Confira os dados do depósito"
                  amount={boletoDepositData.amount}
                  beneficiaryName={boletoDepositData.beneficiaryName || ''}
                />
      
                <div className="flex flex-col gap-3">
                  <div className='flex mt-2'>
                    <Button color='primary' className={`w-full`}>
                      <p>Gerar boleto</p>
                    </Button>
                  </div>
                </div>
              </Card>
            </Container>
          )}
        </>
      );
      
}