'use client'

import React, { useState } from 'react';
import { FaBarcode, FaEye, FaEyeSlash, FaMoneyBill, FaMoneyBillTransfer } from "react-icons/fa6";
import { Card } from 'flowbite-react';
import { MiniCard } from './MiniCard';
import Link from 'next/link';

export default function BankAccountCard() {
  const [isEyeOpen, setEyeOpen] = useState(true)
  const handleClick = () => {
    setEyeOpen(!isEyeOpen)
  };

  return (
    <Card className="p-3">
      <div className="flex justify-between mt-3">
        <p className="text-lg font-mono font-semibold dark:text-white">Saldo Atual</p>
        <Link href={'/accounts'} className="text-lg font-mono font-bold text-primary-2 hover:text-primary-1">Ver extrato</Link>

      </div>

      <div className='flex space-x-6'>
        <p className="text-3xl font-mono font-bold dark:text-white"> {isEyeOpen ? 'R$ 892.337.21' : '********'} </p>

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

      <div className='flex justify-between mt-5'>

        <MiniCard title={'Transferir'} icon={FaMoneyBillTransfer} />
        <MiniCard title={'Pagar'} icon={FaBarcode} />
        <MiniCard title={'Depositar'} icon={FaMoneyBill} />

      </div>
    </Card>
  )
}