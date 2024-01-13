'use client'

import { CardPaymentProvider } from "../context/CardPaymentContext"
import { PixPaymentProvider } from "../context/PixPaymentContext"
import { TransactionProvider } from "../context/TransferContext"

export const Providers = ({ children }: { children: React.ReactNode }) => {
    return (
        <TransactionProvider>
            <PixPaymentProvider>
                <CardPaymentProvider>
                    {children}
                </CardPaymentProvider>
            </PixPaymentProvider>
        </TransactionProvider>
    )
}