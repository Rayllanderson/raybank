import React from 'react';
import NoCreditCardCard from './NoCreditCardCard';
import WithCreditCardCard from './WithCreditCardCard';
import { Card } from './Card';
import CardHeader from './CardHeader';
import { FaAngleRight } from 'react-icons/fa6';
import Link from 'next/link';
import { CardMoney } from './CardMoney';
import LoadingDiv from '../LoadingDiv';


export default function CreditCardCardLoading() {
  return (
    <Card>
      <CardHeader title='Cartão de Crédito' linkHref='/cards' linkObject={(
        <FaAngleRight className="w-6 h-6 hover:scale-105 text-primary-2" />)} />

      <div className='mt-1 flex flex-col gap-2'>

        <div className="flex">
          <p className="text-lg font-mono dark:text-white">Fatura Atual</p>
        </div>

        <Link href={"/cards"} className="space-y-1">
          <div className={`w-28 h-8`}>
            <LoadingDiv className='rounded-md h-full' />
          </div>
          <div className='flex items-center space-x-3'>
            <p className="text-md font-mono dark:text-white">Limite Disponível</p>
            <div className={`w-32`}>
              <LoadingDiv className='rounded-md h-full' />
            </div>
          </div>
        </Link>

      </div>
    </Card>
  )
}