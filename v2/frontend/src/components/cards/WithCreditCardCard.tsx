import React from 'react';
import { FaAngleRight } from 'react-icons/fa6';
import CardHeader from './CardHeader';
import { CardMoney } from './CardMoney';
import Link from 'next/link';
import { Card } from './Card';
import { CardDetails, Card as CreditCard } from '@/types/Card';
import { getCreditCard } from '@/services/CardService';


export default async function WithCreditCardCard({card}: {card: CreditCard}) {
  const cardDetails: CardDetails = await getCreditCard()
  
  return (
    <Card>

      <CardHeader title='Cartão de Crédito' linkHref='/cards' linkObject={(
        <FaAngleRight className="w-6 h-6 hover:scale-105 text-primary-2" />)} />

      <div className='mt-1 flex flex-col gap-2'>

        <div className="flex">
          <p className="text-lg font-mono dark:text-white">Fatura Atual</p>
        </div>

        <Link href={"/cards"} className="space-y-1">
          <div>
            <CardMoney value={cardDetails.invoiceValue ?? 0} className='text-c-blue-1' darkColor='dark:text-c-blue-1' />
          </div>
          <div className='flex items-center space-x-3'>
            <p className="text-md font-mono dark:text-white">Limite Disponível</p>
            <CardMoney size="text-md" value={cardDetails.availableLimit} className='text-c-green-1 ' darkColor='dark:text-c-green-1'/>
          </div>
        </Link>

      </div>
    </Card>
  )
}