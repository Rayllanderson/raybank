'use client'

import React from 'react'
import { SidebarProvider } from './context/SidebarContext';
import Header from './components/Header';
import SideBar from './components/SideBar';
import BankAccountCard from './components/cards/BankAccountCard';
import CreditCardCard from './components/cards/CreditCardCard';
import { Button, Card } from 'flowbite-react';
import CardHeader from './components/cards/CardHeader';
import { MiniCard } from './components/cards/MiniCard';
import { FaPix } from 'react-icons/fa6';
import { LiaBarcodeSolid } from "react-icons/lia";

import { CiBarcode } from "react-icons/ci";

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

              
            </div>

          </div>
        </main>
      </div>
    </SidebarProvider>

  )
}
