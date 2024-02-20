import React from 'react'
import { getAuthAccount } from '@/services/AccountService';
import TransferFormLoading from '@/components/loading/TransferFormLoading';
import dynamic from 'next/dynamic';


const TransferForm = dynamic(() => import('./TransferForm'), {
    ssr: false,
    loading: () => <TransferFormLoading />
})

export default async function page() {

    const account = await getAuthAccount();

    return <TransferForm balance={account.account.balance} />
}