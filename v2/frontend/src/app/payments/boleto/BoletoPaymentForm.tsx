'use client';
import { Container } from '@/app/components/Container';
import TextHeaderForm from '@/app/components/TextHeaderForm';
import { Card } from '@/app/components/cards/Card';
import InputText from '@/app/components/inputs/InputText';
import { useBoletoPayment } from '@/app/context/BoletoPaymentContext';
import { usePixPayment } from '@/app/context/PixPaymentContext';
import { Button } from 'flowbite-react';
import Link from 'next/link';
import React, { useEffect, useRef, useState } from 'react'
import { FaArrowRight } from 'react-icons/fa6';

export default function BoletoPaymentForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const { barCode, setBarCode } = useBoletoPayment();

    useEffect(() => {
        inputRef?.current?.focus();
        const value = inputRef.current?.value
        setIsButtonDisabled(value !== undefined && value?.length < 46)
    }, []);

    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    function onInputChange(e: any) {
        const value = e.target.value.replace(/[^\d]/g, '');
        const isValid = value?.length > 46
        setIsButtonDisabled(!isValid)
        setBarCode(value)
    }

    return (
        <Container>
            <Card >
                <TextHeaderForm title='Digite o código do boleto que quer pagar' />

                <div className="mt-2 flex flex-col gap-3">
                    <InputText placeholder='Código de barras' value={barCode || ''} 
                    inputMode="numeric"
                    pattern="[0-9]*" 
                    ref={inputRef} 
                    onChange={onInputChange} 
                    maxLength={48} />

                    <div className='flex justify-end'>
                        {isButtonDisabled ? (
                            <NextButton isDisabled={isButtonDisabled} />
                        ) : (
                            <Link href='/payments/boleto/confirm'>
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