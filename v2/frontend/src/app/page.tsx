

import React from 'react'
import { SidebarProvider } from './context/SidebarContext';
import Header from './components/Header';
import SideBar from './components/SideBar';
import { AcademicCapIcon } from '@heroicons/react/24/solid';
import { FaMoneyBillTransfer, FaEyeSlash } from "react-icons/fa6";
import { Card } from 'flowbite-react';

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
              <Card className="p-3">
                <div className="flex justify-between mt-3">
                  <p className="text-lg font-mono font-semibold dark:text-white">Saldo Atual</p>
                  <p className="text-lg font-mono font-bold text-pink-600 cursor-pointer">Ver extrato</p>
                </div>
                <div className='flex'>
                  <p className="text-3xl font-mono font-bold dark:text-white">R$ 2.337.24</p>
                  <div className='flex justify-center items-center ml-4 cursor-pointer'>
                    <FaEyeSlash className='text-pink-600 w-7 h-7' />
                  </div>
                </div>

                <div className='flex justify-between mt-5'>

                  {["Transferir", "Pagar", "Depositar"].map((i) => {
                    return <Card key={i} className="text-pink-600 bg-gray-100 hover:scale-105 transition-all p-1 cursor-pointer w-24 h-24 md:w-28 lg:w-32">
                      <div className='flex justify-center items-center'>
                        <FaMoneyBillTransfer className='w-6 h-6' />
                      </div>
                      <div className='flex justify-center'>
                        <p className='text-sm md:text-md font-mono font-semibold'>{i}</p>
                      </div>
                    </Card>
                  })}




                </div>
              </Card>

            </div>

          </div>
        </main>
      </div>
    </SidebarProvider>

  )
}
