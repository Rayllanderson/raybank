'use client'

import React from 'react'
import { SidebarProvider } from './context/SidebarContext';
import Header from './components/Header';
import SideBar from './components/SideBar';
import BankAccountCard from './components/cards/BankAccountCard';
import CreditCardCard from './components/cards/CreditCardCard';
import { Card } from 'flowbite-react';
import CardHeader from './components/cards/CardHeader';
import { MiniCard } from './components/cards/MiniCard';
import { FaPix } from 'react-icons/fa6';
import { LiaBarcodeSolid } from "react-icons/lia";

import { CiBarcode } from "react-icons/ci";
import LinkButton from './components/Buttons/Button';

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
            <div className="flex flex-col h-screen space-y-10  max-w-full">
              <BankAccountCard />

              <CreditCardCard />

              <div className='flex justify-between space-x-5'>
                <Card className='p-3 w-[50%]'>
                  <div className='flex justify-center items-center'>
                    <FaPix className='w-6 h-6 text-primary-2' />
                  </div>
                  <div className='flex flex-col justify-center items-center'>
                    <p className="text-lg text-center font-mono font-semibold dark:text-white">Pix</p>
                    <LinkButton text='Acessar' href='/pix' gradientMonochrome='purple' />
                  </div>
                </Card>

                <Card className='w-[50%] '>
                  <div className='flex justify-center items-center'>
                    <LiaBarcodeSolid className='w-8 h-8 text-primary-2' />
                  </div>
                  <div className='flex flex-col justify-center items-center'>
                    <p className="text-lg text-center font-mono font-semibold dark:text-white">Boletos</p>
                    <LinkButton text='Acessar' href='/boletos' gradientMonochrome='purple' />
                  </div>
                </Card>
              </div>
            </div>

          </div>
        </main>
      </div>
    </SidebarProvider>

  )
}
