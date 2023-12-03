import React from 'react';
import { FaAngleRight } from 'react-icons/fa6';
import CardHeader from './CardHeader';
import { CardMoney } from './CardMoney';
import Link from 'next/link';
import { Card } from './Card';
import { Button } from 'flowbite-react';
import LinkButton from '../Buttons/LinkButton';


export default function NoCreditCardCard() {
  return (
    <Card>

      <CardHeader title='Cartão de Crédito' />

      <div className='mt-5 flex flex-col gap-2'>

        <p className='text-lg font-mono dark:text-white'>Sem cartão de crédito?</p>
        <LinkButton gradientMonochrome={'purple'} href='/cards'> Pedir Cartão </LinkButton>
      </div>
    </Card>
  )
}