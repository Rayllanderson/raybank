import React, { FormEvent } from 'react'
import { Container } from '../../components/Container';
import { Card } from '../../components/cards/Card';
import { CardDetails } from '@/types/Card';
import { getCreditCardOrNullIfNotFound } from '@/services/CardService';
import { redirect } from 'next/navigation';
import CreatePurchaseForm from './CreatePurchaseForm';

export default async function page() {
    const card: CardDetails | null = await getCreditCardOrNullIfNotFound(true);

    if(!card) {
        redirect('/cards')
    }


    return (
        <Container>
            <Card >
                <CreatePurchaseForm card={card} />
            </Card>
        </Container>
    )
}