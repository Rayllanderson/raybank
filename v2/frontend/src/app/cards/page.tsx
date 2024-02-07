import { getCreditCard } from '@/services/CardService';
import { WithCreditCard } from './WithCreditCard';
import { WithoutCreditCard } from './WithoutCreditCard';
import { CardDetails } from '@/types/Card';

export default async function page() {

    const card: CardDetails = await getCreditCard();

    return (
        <WithCreditCard card={card}/>
    )
}

