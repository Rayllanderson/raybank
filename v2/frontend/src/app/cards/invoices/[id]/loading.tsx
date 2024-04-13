import { Container } from '@/components/Container'
import React from 'react'
import { Invoice } from '@/types/Invoice'
import { formatInvoiceStatus, getBgColor } from '@/utils/InvoiceUtil'
import { formatDate } from '@/utils/DateFormatter'
import { MoneyFormatter } from '@/utils/MoneyFormatter'
import { InvoiceTransactionCard } from './InvoiceTransactionCard'
import { findInvoiceById, findInvoiceOrNull } from '@/services/InvoiceService'
import { notFound } from 'next/navigation'
import { InvoiceTransactionCardLoading } from '../../../../components/loading/InvoiceTransactionCardLoading'
import LoadingDiv from '@/components/LoadingDiv'

export default async function loading() {

  return (
    <Container >
      <div className="flex justify-center">
        <header className={`w-full max-w-sm md:max-w-md lg:max-w-lg p-3 h-28 rounded-sm md:rounded-md bg-gray-300 dark:bg-black-3
        relative overflow-hidden shadow-lg before:absolute before:inset-0 before:-translate-x-full before:bg-gradient-to-r before:from-transparent before:via-white/20 hover:shadow-lg before:animate-[shimmer_1.5s_infinite]
        `}>
        </header>

        <div className="h-32"></div>
      </div>

      {[1, 2, 3, 4, 5].map(transaction => {
        return (
          <div className="block w-full p-3" key={transaction}>
            <InvoiceTransactionCardLoading key={transaction} />
          </div>
        )
      })
      }

    </Container>
  )
}