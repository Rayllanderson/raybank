'use client';
import { Container } from '@/components/Container';
import { useCardPayment } from '@/context/CardPaymentContext';
import { MoneyFormatter, getValueNumberFromMoneyInput } from '@/utils/MoneyFormatter';
import React, { useEffect, useRef, useState } from 'react'
import CurrencyForm from '../../../components/CurrencyForm';
import { Invoice } from '@/types/Invoice';

export default function InvoicePaymentForm({ invoice }: { invoice: Invoice }) {
    const inputRef = useRef<HTMLInputElement>(null);
    const { amount, setAmount, setInvoice } = useCardPayment();
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    useEffect(() => {
        setInvoice(invoice)
    }, [])

    useEffect(() => {
        inputRef?.current?.focus();
        const value = getValueNumberFromMoneyInput(inputRef.current?.value)
        setIsButtonDisabled(value > invoice.total || value === 0)
    }, []);



    function onInputChange(value: any) {
        const inputNumber = parseFloat(value || '0');
        setAmount(inputNumber)
        setIsButtonDisabled(inputNumber === 0 || inputNumber > invoice.total)
    }

    return (
        <Container>
            <CurrencyForm
                title={'Quanto você quer pagar da sua fatura aberta?'}
                subtitle={`Valor da fatura aberta ${MoneyFormatter.format(invoice.total)}`}
                isButtonDisabled={isButtonDisabled}
                onInputChange={onInputChange}
                amount={amount}
                inputRef={inputRef}
                href='/payments/invoice/method'
            />
        </Container>
    )
}