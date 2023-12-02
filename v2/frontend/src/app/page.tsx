

import React from 'react'
import { SidebarProvider } from './context/SidebarContext';
import Header from './components/Header';
import SideBar from './components/SideBar';
import { AcademicCapIcon } from '@heroicons/react/24/solid';
import BankAccountCard from './components/cards/BankAccountCard';
import { Card } from 'flowbite-react';
import { FaAngleRight } from 'react-icons/fa6';
import CardHeader from './components/cards/CardHeader';
import { CardMoney } from './components/cards/CardMoney';
import Link from 'next/link';

export default function page() {
  return (
    <SidebarProvider>
      <Header />
      <div className="flex dark:bg-gray-900">
        <div className='order-1 fixed'>
          <SideBar />
        </div>

        <main className="mx-4 mt-4 mb-24 w-full h-full order-2 flex justify-center">

          <div className="cards flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">
            <div className="flex flex-col h-screen space-y-10">
              <BankAccountCard />

              <Card className="p-3">

                <CardHeader title='Cartão de Crédito' linkHref='/cards' linkObject={(
                  <FaAngleRight className="w-6 h-6 hover:scale-105 text-primary-2" />)
                } />

                <div className='mt-5 flex flex-col gap-2'>

                  <div className="flex">
                    <p className="text-lg font-mono dark:text-white">Fatura Atual</p>
                  </div>

                  <Link href={"/cards"} className="space-y-1">
                    <div>
                      <CardMoney value={1242.5} className='text-c-blue-1 dark:text-c-blue-1' />
                    </div>
                    <div className='flex items-center space-x-3'>
                      <p className="text-md font-mono dark:text-white">Limite Disponível</p>
                      <CardMoney size="text-md" value={5423} className='text-c-green-1 dark:text-c-green-1' />
                    </div>
                  </Link>

                </div>
              </Card>
            </div>

          </div>
        </main>
      </div>
    </SidebarProvider>

  )
}
