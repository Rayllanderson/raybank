'use client'

import { TransactionProvider } from "../context/TransferContext"

export const Providers = ({children}: {children: React.ReactNode}) => {
    return (
        <TransactionProvider>
            {children}
        </TransactionProvider>
    )
}