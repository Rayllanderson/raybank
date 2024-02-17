'use client';
import { Container } from '@/components/Container';
import TextHeaderForm from '@/components/TextHeaderForm';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { usePixPayment } from '@/context/PixPaymentContext';
import { Button } from 'flowbite-react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import React, { useEffect, useRef, useState } from 'react'
import toast from 'react-hot-toast';
import { FaArrowRight } from 'react-icons/fa6';

export default function PixPaymentForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const { setQrCode, loading, findPix } = usePixPayment();
    const router = useRouter()

    useEffect(() => {
        inputRef?.current?.focus();
        const value = inputRef.current?.value
        setIsButtonDisabled(value !== undefined && value.length < 120)
    }, []);

    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    function onInputChange(e: any) {
        const value = e.target.value === undefined ? '' : e.target.value;
        const isValid = value?.length > 120
        setIsButtonDisabled(!isValid || loading)
        if (isValid) {
            setQrCode(value)
        }
    }

    async function onClick() {
        setIsButtonDisabled(true)
        const response = await findPix();

        if (!response) {
            toast.error('Nenhum QrCode encontrado com as informações fornecidas')
            return;
        }

        if (response.status !== 'WAITING_PAYMENT') {
            toast.error('Pix não disponível para pagamento')
            return
        }

        router.push('/payments/pix/confirm')
    }

    return (
        <Container>
            <Card >
                <TextHeaderForm title='Insira o código do Pix Copia e Cola' />

                <div className="mt-2 flex flex-col gap-3">
                    <InputText placeholder='Cole o código aqui' ref={inputRef} onChange={onInputChange} maxLength={400} />

                    <div className='flex justify-end'>
                        <Button color='primary' disabled={isButtonDisabled} onClick={onClick}>
                            <FaArrowRight className='w-6 h-6' />
                        </Button>
                    </div>
                </div>
            </Card>
        </Container>
    )
}