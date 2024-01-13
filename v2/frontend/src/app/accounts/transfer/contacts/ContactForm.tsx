'use client';
import { ContactCard } from '@/app/components/ContactCard';
import { Container } from '@/app/components/Container';
import PreviousPageButton from '@/app/components/PreviousPageButton';
import { Card } from '@/app/components/cards/Card';
import { CurrencyInput } from '@/app/components/inputs/InputMoney';
import InputText from '@/app/components/inputs/InputText';
import { useTransferTransactionContext } from '@/app/context/TransferContext';
import { Contact } from '@/app/types/Contact';
import { MoneyFormatter, getValueNumberFromMoneyInput } from '@/app/utils/MoneyFormatter';
import { Button, TextInput } from 'flowbite-react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import React, { useEffect, useRef, useState } from 'react'
import { FaArrowRight } from 'react-icons/fa6';
import { contacts } from './mock';
import { isPixKeyValid } from '@/app/validators/pixKeyValidator';

function isAccountNumber(v: string): boolean {
    const regex = /^\d{9}$/;
    return regex.test(v);
}

export default function ContactForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const router = useRouter();
    const { transaction, setBeneficiary } = useTransferTransactionContext();

    useEffect(() => {
        if (transaction.amount === 0) {
            // router.push('/accounts/transfer') for now is disabled, just for now, rs
        }
    }, [transaction, router]);

    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    function onInputChange(event: any) {
        const value = event.target.value || '';
        const isPixValid = isPixKeyValid(value);
        const isAccountNumberValid = isAccountNumber(value);
        setIsButtonDisabled(!(isPixValid || isAccountNumberValid));
    }

    function onClick() {
        console.log('click')
    }

    return (
        <Container>
            <Card >
                <header className="flex flex-col gap-3">
                    <div>
                        <PreviousPageButton />
                    </div>
                    <div className="text-start">
                        <h1 className="text-lg md:text-xl lg:text-2xl font-semibold">Para quem você quer transferir {MoneyFormatter.format(transaction.amount)}?</h1>
                        <p className='text-md md:text-lg text-gray-500 dark:text-gray-400'>Encontre um contato na sua lista ou inicie uma nova transferência</p>
                    </div>
                </header>

                <div className="mt-4 flex flex-col gap-3">
                    <InputText placeholder='Conta ou chave Pix' ref={inputRef} onChange={onInputChange} />

                    <div className='flex justify-end'>
                        {isButtonDisabled ? (
                            <NextButton isDisabled={isButtonDisabled} />
                        ) : (
                            <Link href='/accounts/transfer/contacts'>
                                <NextButton isDisabled={isButtonDisabled} onClick={onClick} />
                            </Link>
                        )}
                    </div>
                </div>
            </Card>

            <div className='mt-8 p-1'>
                <h1 className="text-xl font-semibold font-mono">Todos os contatos</h1>
                <div className="space-y-4 mt-5 flex flex-col">
                    {
                        contacts.map(contact => {
                            return (
                                <button title={contact.name} key={contact.id} className="p-0 m-0 bg-transparent border-none cursor-pointer text-current hover:scale-[1.03] transform transition-transform" >
                                    <ContactCard contact={contact} key={contact.id} />
                                </button>
                            )
                        })
                    }
                </div>
            </div>

        </Container>
    )
}

const NextButton = ({ isDisabled, onClick }: { isDisabled: boolean, onClick?: () => void }) => (
    <Button color='primary' disabled={isDisabled} onClick={onClick}>
        <FaArrowRight className='w-6 h-6' />
    </Button>
);