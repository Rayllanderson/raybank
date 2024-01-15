import React from 'react';
import { FaAngleRight } from 'react-icons/fa6';
import CardHeader from './CardHeader';
import { CardMoney } from './CardMoney';
import Link from 'next/link';
import { Card } from './Card';


export default function WithCreditCardCard() {
  return (
    <Card>

      <CardHeader title='Cartão de Crédito' linkHref='/cards' linkObject={(
        <FaAngleRight className="w-6 h-6 hover:scale-105 text-primary-2" />)} />

      <div className='mt-4 flex flex-col gap-2'>

        <div className="flex">
          <p className="text-lg font-mono dark:text-white">Fatura Atual</p>
        </div>

        <Link href={"/cards"} className="space-y-1">
          <div>
            <CardMoney value={1242.5} className='text-c-blue-1' darkColor='dark:text-c-blue-1' />
          </div>
          <div className='flex items-center space-x-3'>
            <p className="text-md font-mono dark:text-white">Limite Disponível</p>
            <CardMoney size="text-md" value={5423} className='text-c-green-1 ' darkColor='dark:text-c-green-1'/>
          </div>
        </Link>

      </div>
    </Card>
  )
}