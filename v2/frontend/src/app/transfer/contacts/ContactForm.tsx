'use client';
import { ContactCard } from '@/components/ContactCard';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { useTransferTransactionContext } from '@/context/TransferContext';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { Button } from 'flowbite-react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useRef, useState } from 'react'
import { contacts } from './mock';
import { isPixKeyValid } from '@/validators/PixKeyValidator';
import { getPixKeyTypeAsStringForTransfer } from '@/utils/PixKeyUtil';
import TextHeaderForm from '@/components/TextHeaderForm';
import { Contact } from '@/types/Contact';

function isAccountNumber(v: string): boolean {
    const regex = /^\d{9}$/;
    return regex.test(v);
}

export default function ContactForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const router = useRouter();
    const { transaction, setBeneficiary, setBeneficiaryType, setBeneficiaryName } = useTransferTransactionContext();

    const isInvalidTransaction = useCallback(() => {
        return transaction.amount === 0
    }, [transaction])

    useEffect(() => {
        if (isInvalidTransaction()) {
            router.push('/transfer')
        }
    }, [isInvalidTransaction, router]);

    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    function onInputChange(event: any) {
        const value = event.target.value || '';
        const isPixValid = isPixKeyValid(value);
        const isAccountNumberValid = isAccountNumber(value);
        setIsButtonDisabled(!(isPixValid || isAccountNumberValid));
    }

    function getBeneficiaryType(): string | null {
        const value: string = inputRef.current?.value || ''
        if (isAccountNumber(value)) {
            return 'essa Conta'
        }
        return getPixKeyTypeAsStringForTransfer(value)
    }

    function onButtonClick() {
        const value: string | null = inputRef.current?.value || null
        if (value === null) {
            return;
        }
        setBeneficiary(value)
        setBeneficiaryType(isAccountNumber(value) ? 'account' : 'pix')
        setBeneficiaryName(value)
    }

    function onContactClick(contact: Contact) {
        if (contact.id === null) {
            return;
        }
        setBeneficiary(contact.id)
        setBeneficiaryType('contact')
        setBeneficiaryName(contact.name)
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
                    {isButtonDisabled ? (
                      <NextButton isDisabled={isButtonDisabled} />
                    ) : (
                      <Link href='/transfer/confirm' className='w-full flex  justify-center'>
                        <NextButton isDisabled={isButtonDisabled} onClick={onButtonClick} type={getBeneficiaryType()} />
                      </Link>
                    )}
                  </div>
                </div>
              </Card>
      
              <div className='mt-8 p-1'>
                <h1 className="text-xl font-semibold font-mono">Todos os contatos</h1>
                <div className="space-y-4 mt-5 flex flex-col">
                  {contacts.map(contact => (
                    <Link href='/transfer/confirm' key={contact.id} className='w-full' onClick={() => onContactClick(contact)}>
                      <button
                        title={contact.name}
                        key={contact.id}
                        className="p-0 m-0 bg-transparent border-none cursor-pointer text-current hover:scale-[1.03] transform transition-transform w-full"
                      >
                        <ContactCard contact={contact} key={contact.id} />
                      </button>
                    </Link>
                  ))}
                </div>
              </div>
            </Container>
          )}
        </>
      );
}

const NextButton = ({ isDisabled, type, onClick }: { isDisabled: boolean, type?: string | null, onClick?: () => void }) => (
    <Button color='primary' disabled={isDisabled} onClick={onClick} className={`w-full`}>
        {
            isDisabled ? <p>Transferir</p> : <p>Transferir para {type}</p>
        }
    </Button>
);