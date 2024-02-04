import React from 'react';
import NoCreditCardCard from './NoCreditCardCard';
import WithCreditCardCard from './WithCreditCardCard';
import { Card } from '@/types/Card';


export default function CreditCardCard({card}: {card: Card}) {
  const hasCreditCard: boolean = card !== null;
  return (
    hasCreditCard ?
      <WithCreditCardCard card={card}/>
      :
      <NoCreditCardCard />
  )
}