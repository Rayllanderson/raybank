import { FaPix } from 'react-icons/fa6';
import { LiaBarcodeSolid } from "react-icons/lia";
import Header from '../components/Header';
import SideBar from '../components/SideBar';
import BankAccountCard from '../components/cards/BankAccountCard';
import CreditCardCard from '../components/cards/CreditCardCard';
import { SidebarProvider } from '../context/SidebarContext';

import MediumCard from '../components/cards/MediumCard';
import { getServerAuthSession } from './api/auth/[...nextauth]/options';
import Session from '@/types/Session';
import { AccountResponse } from '@/types/Account';
import { getAccount } from '@/services/AccountService';

export default async function page() {
  const authSession: Session = await getServerAuthSession();
  const userData: AccountResponse = await getAccount(authSession.token);


  return (
    <SidebarProvider>
      <Header />
      <div className="flex">
        <div className='order-1 fixed z-50'>
          <SideBar />
        </div>

        <main className="mx-4 mt-5 sm:mt-5 md:mt-8 lg:mt-8 mb-24 w-full h-full order-2 flex justify-center">

          <div className="cards flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">
            <div className="flex flex-col h-screen space-y-10  max-w-full">
              <BankAccountCard account={userData.account}/>

              <CreditCardCard card={userData.account.card}/>

              <div className='flex justify-between space-x-5'>
                <MediumCard title='Pix'
                  icon={<FaPix className='w-6 h-6 text-primary-2' />} href='/pix' />

                <MediumCard title='Boletos'
                  icon={<LiaBarcodeSolid className='w-8 h-8 text-primary-2' />} href='/boletos' />
              </div>
            </div>

          </div>
        </main>
      </div>
    </SidebarProvider>

  )
}
