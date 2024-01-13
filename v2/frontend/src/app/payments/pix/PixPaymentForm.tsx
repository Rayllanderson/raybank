'use client';
import { Container } from '@/app/components/Container';
import PreviousPageButton from '@/app/components/PreviousPageButton';
import TextHeaderForm from '@/app/components/TextHeaderForm';
import { Card } from '@/app/components/cards/Card';
import { CurrencyInput } from '@/app/components/inputs/InputMoney';
import InputText from '@/app/components/inputs/InputText';
import { usePixPayment } from '@/app/context/PixPaymentContext';
import { useTransferTransactionContext } from '@/app/context/TransferContext';
import { MoneyFormatter, getValueNumberFromMoneyInput } from '@/app/utils/MoneyFormatter';
import { Button } from 'flowbite-react';
import Link from 'next/link';
import React, { useEffect, useRef, useState } from 'react'
import { FaArrowRight } from 'react-icons/fa6';

const saldo = 542.89

export default function PixPaymentForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const {setQrCode} = usePixPayment();

    useEffect(() => {
        inputRef?.current?.focus();
        const value = inputRef.current?.value
        console.log(value !== undefined && value.length < 140)
        setIsButtonDisabled(value !== undefined && value.length < 140)
    }, []);

    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    function onInputChange(e: any) {
        const value = e.target.value === undefined ? '' : e.target.value;
        const isValid = value?.length > 140
        setIsButtonDisabled(!isValid)
        if (isValid) {
            setQrCode(value)
        }
    }

    return (
        <Container>
            <Card >
                <TextHeaderForm title='Insira o código do Pix Copia e Cola' />

                <div className="mt-2 flex flex-col gap-3">
                    <InputText placeholder='Cole o código aqui' ref={inputRef} onChange={onInputChange} />

                    <div className='flex justify-end'>
                        {isButtonDisabled ? (
                            <NextButton isDisabled={isButtonDisabled} />
                        ) : (
                            <Link href='/payments/pix/confirm'>
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