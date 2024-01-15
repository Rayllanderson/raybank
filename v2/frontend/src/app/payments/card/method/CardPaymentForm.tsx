'use client';
import { Container } from '@/components/Container';
import PreviousPageButton from '@/components/PreviousPageButton';
import { Card } from '@/components/cards/Card';
import { useCardPayment } from '@/context/CardPaymentContext';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { useRouter } from 'next/navigation';
import React, { useEffect } from 'react'
import { FaAngleRight, FaBarcode, FaMoneyBill } from 'react-icons/fa6';

const fatura = 542.89

export default function CardPaymentForm() {
    const router = useRouter();
    const { amount, setPaymentMethod } = useCardPayment();
    
    useEffect(() => {
        if (amount === 0) {
            router.push('/payments/card')
        }
    }, [amount, router]);
    

    const setPaymentMethodOnClick = (v:'account' | 'boleto') => {
        setPaymentMethod(v)
    }

    return (
        <Container>
            <Card >
                <header className="flex flex-col gap-3">
                    <div>
                        <PreviousPageButton />
                    </div>
                    <div className="text-start">
                        <h1 className="text-lg md:text-xl lg:text-2xl font-semibold">Você está pagando</h1>
                        <h1 className="text-2xl md:text-3xl lg:text-4xl font-semibold text-primary-2">{MoneyFormatter.format(amount)}</h1>
                        <p className='text-md md:text-lg text-gray-500 dark:text-gray-400'>Total da fatura aberta: {MoneyFormatter.format(fatura)}</p>
                    </div>
                </header>
                <div className='flex flex-col justify-between items-center mt-2 space-y-2'>
                    <CardPaymentMethod title="Pague com saldo da conta" method='account' onClick={setPaymentMethodOnClick} icon={FaMoneyBill}/>
                    <CardPaymentMethod title="Gerar um boleto" method='boleto' onClick={setPaymentMethodOnClick} icon={FaBarcode}/>
                </div>
            </Card>
        </Container>
    )
}

function CardPaymentMethod({ title, method, icon: Icon, onClick }: { title: string, icon:any, method:'account' | 'boleto', onClick: (v:'account' | 'boleto') => void; }) {
    return (
        <button onClick={() => onClick(method)} className='w-full hover:scale-[1.03] transition-transform'>
            <div className='flex justify-between items-center'>
                <div className="flex justify-between items-center space-x-3">
                    <div>
                        <Icon className='h-6 w-6 text-gray-800 dark:text-gray-300' />
                    </div>
                    <div>
                        <p className='text-lg'>{title}</p>
                    </div>
                </div>
                <div>
                    <FaAngleRight />
                </div>
            </div>
        </button>
    )
}