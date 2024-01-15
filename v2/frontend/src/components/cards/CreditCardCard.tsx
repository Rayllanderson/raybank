import React from 'react';
import NoCreditCardCard from './NoCreditCardCard';
import WithCreditCardCard from './WithCreditCardCard';


const hasCreditCard: boolean = true

export default function CreditCardCard() {
  return (
    hasCreditCard ?
      <WithCreditCardCard />
      :
      <NoCreditCardCard />
  )
}