'use client';
import { Container } from '@/app/components/Container';
import { Card } from '@/app/components/cards/Card';
import { CurrencyInput } from '@/app/components/inputs/InputMoney';
import { MoneyFormatter } from '@/app/utils/MoneyFormatter';
import { Button, TextInput } from 'flowbite-react';
import React, { ChangeEvent, useEffect, useRef, useState } from 'react'
import { FaArrowRight } from 'react-icons/fa6';

const saldo = 542.89

export default function TransferForm() {
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    const [amount, setAmount] = useState<number>(0);

    function onInputChange(value: any) {
        const inputNumber = parseFloat(value || '0');
        setIsButtonDisabled(inputNumber === 0 || (inputNumber > saldo ) )
        setAmount(value);
    }

    return (
        <Container>
            <Card >
            <header className="text-start">
                <h1 className="text-2xl font-semibold font-mono">Qual é o valor da transferência?</h1>
                <p className='font-semibold text-lg text-gray-600 dark:text-gray-200'>Saldo disponível em conta {MoneyFormatter.format(saldo)}</p>
            </header>

            <div className="mt-4 flex flex-col gap-3">
                <CurrencyInput value={amount} onValueChange={onInputChange} />

                <div className='flex justify-end'>
                    <Button color='primary' disabled={isButtonDisabled}>
                        <FaArrowRight className='w-6 h-6' />
                    </Button>
                </div>
            </div>
            </Card>
        </Container>
    )
}

