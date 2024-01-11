'use client';
import { Container } from '@/app/components/Container';
import { CurrencyInput } from '@/app/components/inputs/InputMoney';
import { MoneyFormatter } from '@/app/utils/MoneyFormatter';
import { Button, TextInput } from 'flowbite-react';
import React, { ChangeEvent, useEffect, useRef, useState } from 'react'
import MoneyForm from './MoneyForm';
import { FaArrowRight } from 'react-icons/fa6';
import TransferForm from './TransferForm';


export default function page() {
    return <TransferForm />
}