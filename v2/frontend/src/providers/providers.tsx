'use client'

import { AccountDepositProvider } from "@/context/AccountDepositContext"
import { BoletoDepositProvider } from "../context/BoletoDepositContext"
import { BoletoPaymentProvider } from "../context/BoletoPaymentContext"
import { CardPaymentProvider } from "../context/CardPaymentContext"
import { PixDepositProvider } from "../context/PixDepositContext"
import { PixPaymentProvider } from "../context/PixPaymentContext"
import { TransactionProvider } from "../context/TransferContext"
import { SessionProvider } from "next-auth/react"
import { CardProvider } from "@/context/CreateCardContext "

export const Providers = ({ children }: { children: React.ReactNode }) => {
    return (
        <SessionProvider>
            <TransactionProvider>
                <PixPaymentProvider>
                    <CardPaymentProvider>
                        <BoletoPaymentProvider>
                            <PixDepositProvider>
                                <BoletoDepositProvider>
                                    <AccountDepositProvider>
                                        <CardProvider >
                                            {children}
                                        </CardProvider>
                                    </AccountDepositProvider>
                                </BoletoDepositProvider>
                            </PixDepositProvider>
                        </BoletoPaymentProvider>
                    </CardPaymentProvider>
                </PixPaymentProvider>
            </TransactionProvider>
        </SessionProvider>
    )
}