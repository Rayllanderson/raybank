'use client'

import { BoletoPaymentProvider } from "../context/BoletoPaymentContext"
import { CardPaymentProvider } from "../context/CardPaymentContext"
import { PixDepositProvider } from "../context/PixDepositContext"
import { PixPaymentProvider } from "../context/PixPaymentContext"
import { TransactionProvider } from "../context/TransferContext"

export const Providers = ({ children }: { children: React.ReactNode }) => {
    return (
        <TransactionProvider>
            <PixPaymentProvider>
                <CardPaymentProvider>
                    <BoletoPaymentProvider>
                        <PixDepositProvider>
                            {children}
                        </PixDepositProvider>
                    </BoletoPaymentProvider>
                </CardPaymentProvider>
            </PixPaymentProvider>
        </TransactionProvider>
    )
}