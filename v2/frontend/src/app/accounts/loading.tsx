import BankAccountCardLoading from '@/components/loading/BankAccountCardLoading'
import { StatementsLoading } from '@/components/loading/StatementsLoading'
import React from 'react'

export default function loading() {
    return (
        <div className="cards flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">
            <BankAccountCardLoading withLinkHeader={false} />

            <div className="mt-8 p-1">
                <StatementsLoading />
            </div>
        </div>
    )
}
