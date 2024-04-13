'use client';
import { Container } from '@/components/Container';
import CurrencyForm from '@/components/CurrencyForm';
import { useAccountDeposit } from '@/context/AccountDepositContext';
import { getValueNumberFromMoneyInput } from '@/utils/MoneyFormatter';
import React, { useEffect, useRef, useState } from 'react'

export default function DepositAccountForm() {
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        inputRef?.current?.focus();
        const value = getValueNumberFromMoneyInput(inputRef.current?.value)
        setIsButtonDisabled(value === 0)
    }, []);

    const { amount, setAmount } = useAccountDeposit();
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    function onInputChange(value: any) {
        const inputNumber = parseFloat(value || '0');
        setAmount(inputNumber)
        setIsButtonDisabled(inputNumber === 0)
    }

    return (
        <Container>
            <CurrencyForm
                title={'Qual valor você quer depositar?'}
                isButtonDisabled={isButtonDisabled}
                onInputChange={onInputChange}
                amount={amount}
                inputRef={inputRef}
                href='/deposits/account/confirm'
            />
        </Container>
    )
}