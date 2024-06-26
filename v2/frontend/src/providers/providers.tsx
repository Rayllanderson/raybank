'use client'

import { AccountDepositProvider } from "@/context/AccountDepositContext"
import { BoletoDepositProvider } from "../context/BoletoDepositContext"
import { BoletoPaymentProvider } from "../context/BoletoPaymentContext"
import { InvoicePaymentProvider } from "../context/InvoicePaymentContext"
import { PixDepositProvider } from "../context/PixDepositContext"
import { PixPaymentProvider } from "../context/PixPaymentContext"
import { TransactionProvider } from "../context/TransferContext"
import { SessionProvider } from "next-auth/react"
import { CardProvider } from "@/context/CreateCardContext "
import { ChangeCardLimitProvider } from "@/context/ChangeCardLimitContext"
import { StatementProvider } from "@/context/StatementContext"
import { ContactProvider } from "@/context/ContactContex"
import { FindAccountProvider } from "@/context/FindAccountContext"
import { PixRegisterKeyProvider } from "@/context/PixRegisterKeyContext"
import { PixUpdateLimitProvider } from "@/context/PixLimitContext"

export const Providers = ({ children }: { children: React.ReactNode }) => {
    return (
        <SessionProvider>
            <TransactionProvider>
                <PixPaymentProvider>
                    <InvoicePaymentProvider>
                        <BoletoPaymentProvider>
                            <PixDepositProvider>
                                <BoletoDepositProvider>
                                    <AccountDepositProvider>
                                        <CardProvider >
                                            <ChangeCardLimitProvider>
                                                {/* <StatementProvider> */}
                                                <ContactProvider>
                                                    <FindAccountProvider>
                                                        <PixRegisterKeyProvider>
                                                            <PixUpdateLimitProvider>
                                                                {children}
                                                            </PixUpdateLimitProvider>
                                                        </PixRegisterKeyProvider>
                                                    </FindAccountProvider>

                                                </ContactProvider>
                                                {/* </StatementProvider> */}
                                            </ChangeCardLimitProvider>
                                        </CardProvider>
                                    </AccountDepositProvider>
                                </BoletoDepositProvider>
                            </PixDepositProvider>
                        </BoletoPaymentProvider>
                    </InvoicePaymentProvider>
                </PixPaymentProvider>
            </TransactionProvider>
        </SessionProvider>
    )
}