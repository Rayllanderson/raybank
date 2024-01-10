'use client';
import { Container } from '@/app/components/Container';
import { CurrencyInput } from '@/app/components/inputs/InputMoney';
import { MoneyFormatter } from '@/app/utils/MoneyFormatter';
import { TextInput } from 'flowbite-react';
import React, { ChangeEvent, useEffect, useState } from 'react'
import MoneyForm from './MoneyForm';

const saldo = 542.89

export default function page() {


    return (
        <Container>
            <header className="text-start">
                <h1 className="text-2xl font-semibold font-mono">Qual é o valor da transferência?</h1>
                <p className='font-semibold text-lg text-gray-600 dark:text-gray-200'>Saldo disponível em conta {MoneyFormatter.format(saldo)}</p>
            </header>

            <MoneyForm />
        </Container>
    )

}

