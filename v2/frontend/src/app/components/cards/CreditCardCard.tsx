import React from 'react';
import { FaAngleRight } from 'react-icons/fa6';
import CardHeader from './CardHeader';
import { CardMoney } from './CardMoney';
import Link from 'next/link';
import { Card } from './Card';
import NoCreditCardCard from './NoCreditCardCard';
import WithCreditCardCard from './WithCreditCardCard';


const hasCreditCard: boolean = false

export default function CreditCardCard() {
  return (
    hasCreditCard ?
      <WithCreditCardCard />
      :
      <NoCreditCardCard />
  )
}