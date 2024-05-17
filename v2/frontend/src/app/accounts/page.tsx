import React from 'react'
import BankAccountCard from '../../components/cards/BankAccountCard'
import { Statements } from '../../components/Statements';
import { AccountResponse } from '@/types/Account'
import { getAuthAccount } from '@/services/AccountService'
import { notFound } from 'next/navigation';


export default async function page() {
    const userData: AccountResponse | null = await getAuthAccount();

    if (!userData)
        notFound()

    return (
        <div className="cards flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">
            <BankAccountCard withLinkHeader={false} account={userData.account}/>

            <div className="mt-8 p-1">
                <Statements type='account' />
            </div>
        </div>
    )

}

