import React from 'react'
import TransferForm from './TransferForm';
import { getAuthAccount } from '@/services/AccountService';


export default async function page() {

    const account = await getAuthAccount();

    return <TransferForm balance={account.account.balance}/>
}