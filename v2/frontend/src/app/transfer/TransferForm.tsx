'use client';
import { Container } from '@/app/components/Container';
import { useTransferTransactionContext } from '@/app/context/TransferContext';
import { MoneyFormatter, getValueNumberFromMoneyInput } from '@/app/utils/MoneyFormatter';
import { Button } from 'flowbite-react';
import Link from 'next/link';
import React, { useEffect, useRef, useState } from 'react'
import { FaArrowRight } from 'react-icons/fa6';
import CurrencyForm from '../components/CurrencyForm';

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
            <CurrencyForm
                title={'Qual é o valor da transferência?'}
                subtitle={`Saldo disponível em conta ${MoneyFormatter.format(saldo)}`}
                isButtonDisabled={isButtonDisabled}
                onInputChange={onInputChange}
                amount={transaction.amount}
                inputRef={inputRef}
                href='/transfer/contacts'
            />
        </Container>
    )
}


const NextButton = ({ isDisabled }: { isDisabled: boolean }) => (
    <Button color='primary' disabled={isDisabled}>
        <FaArrowRight className='w-6 h-6' />
    </Button>
);