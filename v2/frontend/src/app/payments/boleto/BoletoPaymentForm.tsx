'use client';
import { Container } from '@/components/Container';
import TextHeaderForm from '@/components/TextHeaderForm';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { useBoletoPayment } from '@/context/BoletoPaymentContext';
import { Button } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useEffect, useRef, useState } from 'react'
import toast from 'react-hot-toast';
import { FaArrowRight } from 'react-icons/fa6';

export default function BoletoPaymentForm() {
    const inputRef = useRef<HTMLInputElement>(null);
    const { barCode, setBarCode, findBoleto,loading } = useBoletoPayment();
    const router = useRouter()

    useEffect(() => {
        setIsButtonDisabled(loading)
    }, [loading]);

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

    async function onButtonClick() {
        setIsButtonDisabled(true)
        const response = await findBoleto()

        if (!response) {
            toast.error('Nenhum boleto encontrado com as informações fornecidas')
            return;
        }

        if (response.boleto.status !== 'WAITING_PAYMENT') {
            toast.error('Boleto não disponível para pagamento')
            return
        }

        router.push('/payments/boleto/confirm')
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
                        <Button color='primary' disabled={isButtonDisabled} onClick={onButtonClick}>
                            <FaArrowRight className='w-6 h-6' />
                        </Button>
                    </div>
                </div>
            </Card>
        </Container>
    )
}