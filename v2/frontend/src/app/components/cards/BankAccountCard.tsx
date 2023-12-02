'use client'

import React, { useState } from 'react';
import { FaBarcode, FaEye, FaEyeSlash, FaMoneyBill, FaMoneyBillTransfer } from "react-icons/fa6";
import { MiniCard } from './MiniCard';
import Link from 'next/link';
import { CardMoney } from './CardMoney';
import CardHeader from './CardHeader';
import { Card } from './Card';




export default function BankAccountCard() {
  const [isEyeOpen, setEyeOpen] = useState(true)
  const handleClick = () => {
    setEyeOpen(!isEyeOpen)
  };

  return (
    <Card >
      <CardHeader title='Saldo Atual' linkHref='/accounts' linkObject={'Ver extrato'} />

      <div className='flex space-x-6'>
        <CardMoney value={isEyeOpen ? '1525.2' : '********'} />

        <div className='flex justify-items-start w-[6.5rem]'>
          <button className='flex justify-center items-center transition-all'
            onClick={handleClick}
          >
            {
              isEyeOpen ?
                <FaEyeSlash className='text-primary-2 w-7 h-7 hover:text-primary-1' />
                :
                <FaEye className='text-primary-2 w-7 h-7 hover:text-primary-1' />
            }
          </button>
        </div>
      </div>

      <div className='flex justify-between mt-5 sm:space-x-1'>

        <MiniCard title={'Transferir'} icon={FaMoneyBillTransfer} />
        <MiniCard title={'Pagar'} icon={FaBarcode} />
        <MiniCard title={'Depositar'} icon={FaMoneyBill} />

      </div>
    </Card>
  )
}