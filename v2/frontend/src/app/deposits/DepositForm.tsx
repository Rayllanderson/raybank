'use client'
import { Container } from '@/app/components/Container';
import React from 'react'
import { FaBarcode, FaPix } from 'react-icons/fa6';
import { Card } from '../components/cards/Card';
import ListItem from '../components/ListItem';
import TextHeaderForm from '../components/TextHeaderForm';

export default function DepositForm() {
    return (
        <Container>
            <Card>
                <TextHeaderForm
                    title='Como você quer depositar na sua conta Raybank?'
                />
                <div className='flex flex-col space-y-5'>
                    <ListItem
                        type='link'
                        href='/deposits/pix'
                        icon={FaPix}
                        title='Receber na hora em qualquer dia, sem custo'
                        subtitle='Gere um código Pix para copiar e colar'
                        withSeparator
                    />
                    <ListItem
                        type='link'
                        href='/deposits/boleto'
                        icon={FaBarcode}
                        title='Depositar em dinheiro'
                        subtitle='Gere um boleto para pagar em bancos'
                    />
                </div>
            </Card>
        </Container>
    )
}