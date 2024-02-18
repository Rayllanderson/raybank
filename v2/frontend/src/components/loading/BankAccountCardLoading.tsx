'use client'

import { useState } from 'react';
import { FaBarcode, FaEye, FaEyeSlash, FaMoneyBill, FaMoneyBillTransfer } from "react-icons/fa6";
import { Card } from '../cards/Card';
import CardHeader from '../cards/CardHeader';
import { MiniCard } from '../cards/MiniCard';
import { Account } from '@/types/Account';
import LoadingDiv from '../LoadingDiv';

type Props = {
  withLinkHeader?: boolean
}

export default function BankAccountCardLoading({ withLinkHeader = true }: Props) {
  const [isEyeOpen, setEyeOpen] = useState(true)
  const handleClick = () => {
    setEyeOpen(!isEyeOpen)
  };

  return (
    <Card >
      {
        withLinkHeader ?
          <CardHeader title='Saldo Atual' linkHref='/accounts' linkObject={'Ver extrato'} /> :
          <CardHeader title='Saldo Atual' />
      }

      <div className='flex space-x-6'>
        <div className={`w-28 h-8`}>
          <LoadingDiv className='rounded-md h-full'/>
        </div>

        <div className='flex justify-items-start w-[6.5rem]'>
          <button className='flex justify-center items-center transition-all'
            onClick={handleClick}
          >

            {
              withLinkHeader &&
              (isEyeOpen ?
                <FaEyeSlash className='text-primary-2 w-7 h-7 hover:text-primary-1' />
                :
                <FaEye className='text-primary-2 w-7 h-7 hover:text-primary-1' />
              )
            }
          </button>
        </div>
      </div>

      <div className='flex justify-between mt-4 sm:space-x-1'>

        <MiniCard title={'Transferir'} icon={FaMoneyBillTransfer} href='/transfer' />
        <MiniCard title={'Pagar'} icon={FaBarcode} href='/payments' />
        <MiniCard title={'Depositar'} icon={FaMoneyBill} href='/deposits' />

      </div>
    </Card>
  )
}