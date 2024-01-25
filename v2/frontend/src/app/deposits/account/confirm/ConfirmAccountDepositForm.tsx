'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { ConfirmTransactionHeader } from '@/components/ConfirmTransactionHeader';
import { Button } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect } from 'react'
import { useAccountDeposit } from '@/context/AccountDepositContext';


export default function ConfirmAccountDepositForm() {
    const router = useRouter();
    const { amount } = useAccountDeposit();

    const isDepositInvalid = useCallback(() => {
        return amount === 0
    }, [amount])

    useEffect(() => {
        if (isDepositInvalid()) {
            router.push('/deposits/account')
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
                  amount={amount}
                  beneficiaryName={'KAGUYA SHINOMYA'}
                />
      
                <div className="flex flex-col gap-3">
                  <div className='flex mt-2'>
                    <Button color='primary' className={`w-full`}>
                      <p>Depositar</p>
                    </Button>
                  </div>
                </div>
              </Card>
            </Container>
          )}
        </>
      );
      
}