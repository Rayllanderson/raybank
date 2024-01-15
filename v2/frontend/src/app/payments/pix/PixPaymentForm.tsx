'use client';
import { Container } from '@/components/Container';
import TextHeaderForm from '@/components/TextHeaderForm';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { usePixPayment } from '@/context/PixPaymentContext';
import { Button } from 'flowbite-react';
import Link from 'next/link';
import React, { useEffect, useRef, useState } from 'react'
import { FaArrowRight } from 'react-icons/fa6';

export default function PixPaymentForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const {setQrCode} = usePixPayment();

    useEffect(() => {
        inputRef?.current?.focus();
        const value = inputRef.current?.value
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
                    <InputText placeholder='Cole o código aqui' ref={inputRef} onChange={onInputChange} maxLength={400}/>

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