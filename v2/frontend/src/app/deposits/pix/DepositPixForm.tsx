'use client';
import { Container } from '@/components/Container';
import CurrencyForm from '@/components/CurrencyForm';
import { usePixDepositContext } from '@/context/PixDepositContext';
import { getValueNumberFromMoneyInput } from '@/utils/MoneyFormatter';
import React, { useEffect, useRef, useState } from 'react'

export default function DepositPixForm() {
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        inputRef?.current?.focus();
        const value = getValueNumberFromMoneyInput(inputRef.current?.value)
        setIsButtonDisabled(value === 0)
    }, []);

    const { pixDepositData, setAmount } = usePixDepositContext();
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    function onInputChange(value: any) {
        const inputNumber = parseFloat(value || '0');
        setAmount(inputNumber)
        setIsButtonDisabled(inputNumber === 0)
    }

    return (
        <Container>
            <CurrencyForm
                title={'Qual valor você quer depositar usando Pix?'}
                isButtonDisabled={isButtonDisabled}
                onInputChange={onInputChange}
                amount={pixDepositData.amount}
                inputRef={inputRef}
                href='/deposits/pix/confirm'
            />
        </Container>
    )
}