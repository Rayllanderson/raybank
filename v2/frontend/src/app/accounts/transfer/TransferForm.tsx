'use client';
import { Container } from '@/app/components/Container';
import { Card } from '@/app/components/cards/Card';
import { CurrencyInput } from '@/app/components/inputs/InputMoney';
import { useTransferTransactionContext } from '@/app/context/TransferContext';
import { MoneyFormatter, getValueNumberFromMoneyInput } from '@/app/utils/MoneyFormatter';
import { Button } from 'flowbite-react';
import Link from 'next/link';
import React, { useEffect, useRef, useState } from 'react'
import { FaArrowRight } from 'react-icons/fa6';

const saldo = 542.89

export default function TransferForm() {
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        inputRef?.current?.focus();
        const value = getValueNumberFromMoneyInput(inputRef.current?.value)
        setIsButtonDisabled(value > saldo || value === 0)
    }, []);

    const { transaction, setAmount } = useTransferTransactionContext();
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    function onInputChange(value: any) {
        const inputNumber = parseFloat(value || '0');
        const transactionAmount = setAmount(inputNumber)
        setIsButtonDisabled(transactionAmount === 0 || (transactionAmount > saldo))
    }

    return (
        <Container>
            <Card >
                <header className="text-start">
                    <h1 className="text-lg md:text-xl lg:text-2xl font-semibold">Qual é o valor da transferência?</h1>
                    <p className='text-md md:text-lg text-gray-500 dark:text-gray-400'>Saldo disponível em conta {MoneyFormatter.format(saldo)}</p>
                </header>

                <div className="mt-4 flex flex-col gap-3">
                    <CurrencyInput value={transaction.amount} onValueChange={onInputChange} ref={inputRef} />

                    <div className='flex justify-end'>
                        {isButtonDisabled ? (
                            <NextButton isDisabled={isButtonDisabled} />
                        ) : (
                            <Link href='/accounts'>
                                <NextButton isDisabled={isButtonDisabled} />
                            </Link>
                        )}
                    </div>
                </div>
            </Card>
        </Container>
    )
}


const NextButton = ({ isDisabled }: { isDisabled: boolean }) => (
    <Button color='primary' disabled={isDisabled}>
        <FaArrowRight className='w-6 h-6' />
    </Button>
);