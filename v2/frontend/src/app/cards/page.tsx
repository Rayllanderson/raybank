import { getCreditCard, getCreditCardOrNullIfNotFound } from '@/services/CardService';
import { WithCreditCard } from './WithCreditCard';
import { CardDetails } from '@/types/Card';
import { getServerSession } from 'next-auth';
import { getServerAuthSession } from '../api/auth/[...nextauth]/options';
import { signIn, signOut } from 'next-auth/react';
import { WithoutCreditCard } from './WithoutCreditCard';
import { notFound } from 'next/navigation';

export default async function page() {
    const card: CardDetails | null = await getCreditCardOrNullIfNotFound();

    if(!card) {
        notFound()
    }

    return (
        <WithCreditCard card={card} />
    )
}

