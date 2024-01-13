'use client';
import { Container } from '@/app/components/Container';
import { useCardPayment } from '@/app/context/CardPaymentContext';
import { MoneyFormatter, getValueNumberFromMoneyInput } from '@/app/utils/MoneyFormatter';
import React, { useEffect, useRef, useState } from 'react'
import CurrencyForm from '../../components/CurrencyForm';

const fatura = 542.89

export default function CardPaymentForm() {
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        inputRef?.current?.focus();
        const value = getValueNumberFromMoneyInput(inputRef.current?.value)
        setIsButtonDisabled(value > fatura || value === 0)
    }, []);

    const { amount, setAmount } = useCardPayment();
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    function onInputChange(value: any) {
        const inputNumber = parseFloat(value || '0');
        setAmount(inputNumber)
        setIsButtonDisabled(inputNumber === 0 || inputNumber > fatura)
    }

    return (
        <Container>
            <CurrencyForm
                title={'Quanto você quer pagar da sua fatura aberta?'}
                subtitle={`Valor da fatura aberta ${MoneyFormatter.format(fatura)}`}
                isButtonDisabled={isButtonDisabled}
                onInputChange={onInputChange}
                amount={amount}
                inputRef={inputRef}
                href='/payments/card/method'
            />
        </Container>
    )
}