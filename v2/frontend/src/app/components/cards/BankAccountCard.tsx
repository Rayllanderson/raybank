import React from 'react';
import { FaBarcode, FaEyeSlash, FaMoneyBill, FaMoneyBillTransfer } from "react-icons/fa6";
import { Card } from 'flowbite-react';
import { MiniCard } from './MiniCard';

export default function BankAccountCard() {
  return (
    <Card className="p-3">
      <div className="flex justify-between mt-3">
        <p className="text-lg font-mono font-semibold dark:text-white">Saldo Atual</p>
        <p className="text-lg font-mono font-bold text-primary-1 cursor-pointer">Ver extrato</p>
      </div>
      <div className='flex'>
        <p className="text-3xl font-mono font-bold dark:text-white">R$ 2.337.24</p>
        <div className='flex justify-center items-center ml-4 cursor-pointer'>
          <FaEyeSlash className='text-primary-1 w-7 h-7' />
        </div>
      </div>

      <div className='flex justify-between mt-5'>

        <MiniCard title={'Transferir'} icon={FaMoneyBillTransfer} />
        <MiniCard title={'Pagar'} icon={FaBarcode} />
        <MiniCard title={'Depositar'} icon={FaMoneyBill } />

      </div>
    </Card>
  )
}