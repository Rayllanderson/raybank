import { Container } from '@/components/Container'
import React from 'react'
import { Invoice } from '@/types/Invoice'
import { formatInvoiceStatus, getBgColor } from '@/utils/InvoiceUtil'
import { formatDate } from '@/utils/DateFormatter'
import { MoneyFormatter } from '@/utils/MoneyFormatter'
import { InvoiceTransactionCard } from './InvoiceTransactionCard'
import { findInvoiceById, findInvoiceOrNull } from '@/services/InvoiceService'
import { notFound } from 'next/navigation'
import PrimaryButton from '@/components/Buttons/PrimaryButton'
import Link from 'next/link'
import { Card } from '@/components/cards/Card'

export default async function page({ params }: { params: { id: string } }) {
  const invoice: Invoice | null = await findInvoiceOrNull(params.id)

  if (!invoice) {
    notFound()
  }
  return (
    <Container >
      <div className="flex justify-center mb-2">
        <header className={`w-full max-w-sm md:max-w-md lg:max-w-lg p-[0.20rem] h-26 rounded-lg md:rounded-md bg-[#9B3BDA] ${getBgColor(invoice)}`}>
          <Card>
          <div className="flex flex-col justify-center items-center ">
            <p className="text-xl">{formatInvoiceStatus(invoice.status)}</p>
            <p className="text-xl">{MoneyFormatter.format(invoice.total)}</p>
            <p>Vencimento {formatDate(invoice.dueDate)}</p>
          </div>
          </Card>
        </header>

        <div className="h-32"></div>
      </div>

      {invoice.transactions === undefined &&
        <p className='text-gray-500 dark:text-gray-300 text-center'>Sem transações encontradas</p>
      }

      {invoice.transactions?.map(transaction => {
        return (
          <div className="block w-full p-[0.65rem]" key={transaction.id}>
            <InvoiceTransactionCard transaction={transaction} key={transaction.id} />
          </div>
        )
      })
      }

      {
        
        (invoice.status === 'OPEN' || invoice.status === 'CLOSED' || invoice.status === 'OVERDUE') &&
        <div className="fixed bottom-0 left-0 right-0 flex justify-center items-center mb-4">
          <Link href={`/payments/invoice/${invoice.id}`}
            className='w-[90%] max-w-sm flex justify-center'>
            <PrimaryButton className='max-w-sm'> Pagar Fatura</PrimaryButton>
          </Link>
        </div>
      }

    </Container>
  )
}