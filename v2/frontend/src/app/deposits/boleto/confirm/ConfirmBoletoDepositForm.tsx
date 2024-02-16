'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { ConfirmTransactionHeader } from '@/components/ConfirmTransactionHeader';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect } from 'react'
import { useBoletoDepositContext } from '@/context/BoletoDepositContext';
import toast from 'react-hot-toast';
import PrimaryButton from '@/components/Buttons/PrimaryButton';
import { useSession } from 'next-auth/react';


export default function ConfirmBoletoDepositForm() {
  const router = useRouter();
  const { boletoDepositData, loading, generateBoleto } = useBoletoDepositContext();
  const { data: session } = useSession();

  const isDepositInvalid = useCallback(() => {
    return boletoDepositData.amount === 0
  }, [boletoDepositData])

  useEffect(() => {
    if (isDepositInvalid()) {
      router.push('/deposits/boleto')
    }
  }, [isDepositInvalid, router]);

  async function onButtonClick() {
    const response = await generateBoleto(session?.user?.id, 'account')

    if (response) {
      toast.success('Boleto gerado com sucesso')
      router.replace('/deposits/boleto/success')
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
              amount={boletoDepositData.amount}
              beneficiaryName={session?.user?.name!}
            />

            <div className="flex flex-col gap-3">
              <div className='flex mt-2'>
                <PrimaryButton loading={loading} disabled={loading} className={`w-full`} onClick={onButtonClick}>
                  <p>Gerar boleto</p>
                </PrimaryButton>
              </div>
            </div>
          </Card>
        </Container>
      )}
    </>
  );

}