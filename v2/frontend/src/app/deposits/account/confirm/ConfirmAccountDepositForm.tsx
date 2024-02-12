'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { ConfirmTransactionHeader } from '@/components/ConfirmTransactionHeader';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useState } from 'react'
import { useAccountDeposit } from '@/context/AccountDepositContext';
import { useSession } from 'next-auth/react';
import { deposit } from '@/services/AccountService';
import toast from 'react-hot-toast';
import PrimaryButton from '@/components/Buttons/PrimaryButton';


export default function ConfirmAccountDepositForm() {
  const router = useRouter();
  const { amount, doDeposit, loading} = useAccountDeposit();
  const { data } = useSession()

  const isDepositInvalid = useCallback(() => {
    return amount === 0
  }, [amount])

  useEffect(() => {
    console.log('oh caralho')
    if (isDepositInvalid() && !loading) {
      router.push('/deposits/account')
    }
  }, [isDepositInvalid, router, loading]);

  async function onButtonClick() {
    const response = await doDeposit(data?.token!)
    if (response) {
      toast.success('Depósito Realizado com sucesso')
      router.replace('/deposits/account/success')
    }
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
              beneficiaryName={data?.user.name}
            />

            <div className="flex flex-col gap-3">
              <div className='flex mt-2'>
                <PrimaryButton loading={loading} disabled={loading} className={`w-full`} onClick={onButtonClick}>
                  <p>Depositar</p>
                </PrimaryButton>
              </div>
            </div>
          </Card>
        </Container>
      )}
    </>
  );

}