'use client';
import { Container } from '@/components/Container';
import { useTransferTransactionContext } from '@/context/TransferContext';
import { MoneyFormatter, getValueNumberFromMoneyInput } from '@/utils/MoneyFormatter';
import { Button } from 'flowbite-react';
import React, { useEffect, useRef, useState } from 'react'
import { FaArrowRight } from 'react-icons/fa6';
import CurrencyForm from '../../components/CurrencyForm';

export default function TransferForm({balance}: {balance: number}) {
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        inputRef?.current?.focus();
        const value = getValueNumberFromMoneyInput(inputRef.current?.value)
        setIsButtonDisabled(value > balance || value === 0)
    }, []);

    const { transaction, setAmount } = useTransferTransactionContext();
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    function onInputChange(value: any) {
        const inputNumber = parseFloat(value || '0');
        const transactionAmount = setAmount(inputNumber)
        setIsButtonDisabled(transactionAmount === 0 || (transactionAmount > balance))
    }

    return (
        <Container>
            <CurrencyForm
                title={'Qual é o valor da transferência?'}
                subtitle={`Saldo disponível em conta ${MoneyFormatter.format(balance)}`}
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