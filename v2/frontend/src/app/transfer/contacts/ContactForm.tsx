'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { useTransferTransactionContext } from '@/context/TransferContext';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { Button } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useRef, useState } from 'react'
import { isPixKeyValid } from '@/validators/PixKeyValidator';
import { getPixKeyTypeAsStringForTransfer } from '@/utils/PixKeyUtil';
import TextHeaderForm from '@/components/TextHeaderForm';
import { Contact } from '@/types/Contact';
import ContactsList from './ContactList';
import { useFindAccountContext } from '@/context/FindAccountContext';
import toast from 'react-hot-toast';
import { ImSpinner2 } from 'react-icons/im';

function isAccountNumber(v: string): boolean {
  const regex = /^\d{9}$/;
  return regex.test(v);
}

export default function ContactForm() {
  const inputRef = useRef<HTMLInputElement>(null);
  const router = useRouter();
  const { transaction, setBeneficiary, setBeneficiaryType, setBeneficiaryName } = useTransferTransactionContext();
  const [finished, setFinished] = useState(true)

  const { loading, findAccount } = useFindAccountContext();

  const isInvalidTransaction = useCallback(() => {
    return transaction.amount === 0
  }, [transaction])

  useEffect(() => {
    if (isInvalidTransaction()) {
      router.push('/transfer')
    }
  }, [router, isInvalidTransaction]);


  const [isButtonDisabled, setIsButtonDisabled] = useState(true);

  function onInputChange(event: any) {
    const value = event.target.value || '';
    const isPixValid = isPixKeyValid(value);
    const isAccountNumberValid = isAccountNumber(value);
    setIsButtonDisabled(!(isPixValid || isAccountNumberValid));
  }

  function getBeneficiaryType(): string | null {
    const value: string = inputRef.current?.value || '';
    return isAccountNumber(value) ? 'essa Conta' : getPixKeyTypeAsStringForTransfer(value);
  }

  async function onButtonClick() {
    const value: string | null = inputRef.current?.value || null
    if (value === null) {
      return;
    }

    const type = isAccountNumber(value) ? 'account' : 'pix';
    setBeneficiaryType(type);

    const account = await findAccount(type, value)

    if (!account) {
      toast.error('Nenhuma conta encontrada com o informações fornecidas')
      return;
    }

    setBeneficiaryName(account?.name!)
    setBeneficiary(value)
    router.push('/transfer/confirm')
  }

  async function onContactClick(contact: Contact) {
    if (contact.id === null) {
      return;
    }

    setFinished(false)

    const account = await findAccount('contact', contact.id!)

    if (!account) {
      toast.error('Nenhuma conta encontrada com o informações fornecidas')
      setFinished(true)
      return;
    }
    setBeneficiary(account?.number!)
    setBeneficiaryType('contact')
    setBeneficiaryName(account?.name)
    router.push('/transfer/confirm')
  }

  return (
    <>
      {isInvalidTransaction() && null}

      {!isInvalidTransaction() && (
        <Container>
          <Card>
            <TextHeaderForm
              title={`Para quem você quer transferir ${MoneyFormatter.format(transaction.amount)}?`}
              subtitle='Encontre um contato na sua lista ou inicie uma nova transferência'
            />

            <div className="mt-4 flex flex-col gap-3">
              <InputText placeholder='Conta ou chave Pix' ref={inputRef} onChange={onInputChange} />

              <div className='flex'>
                <NextButton isDisabled={isButtonDisabled || loading || !finished} isLoading={loading || !finished} onClick={onButtonClick} type={getBeneficiaryType()} />
              </div>
            </div>
          </Card>

          <ContactsList onClick={onContactClick} disabled={loading || !finished} />
        </Container>
      )}
    </>
  );
}

const NextButton = ({ isDisabled, isLoading, type, onClick }: { isDisabled: boolean, isLoading: boolean, type?: string | null, onClick?: () => void }) => (
  <Button color='primary' disabled={isDisabled} onClick={onClick} type='submit' className={`w-full`}>
    {
      isDisabled && !isLoading ? <p>Transferir</p> : (isLoading ?
        <ImSpinner2 className="h-5 w-5 animate-spin" /> :
        <p>Transferir para {type}</p>
      )
    }
  </Button>
);