import { Container } from '@/components/Container'
import React from 'react'
import { invoices } from '../mock'
import { Invoice } from '@/types/Invoice'
import { formatInvoiceStatus, getBgColor } from '@/utils/InvoiceUtil'
import { formatDate } from '@/utils/DateFormatter'
import { MoneyFormatter } from '@/utils/MoneyFormatter'
import { InvoiceTransactionCard } from './InvoiceTransactionCard'

export default function page({ params }: { params: { id: string } }) {
  const invoice: Invoice = invoices.filter(it => it.id === params.id)[0]
  return (
    <Container >
      <div className="flex justify-center">
        <header className={`w-full max-w-sm md:max-w-md lg:max-w-lg p-3 h-28 rounded-sm md:rounded-md  ${getBgColor(invoice)}`}>
          <div className="flex flex-col justify-center items-center  text-white">
            <p className="text-xl">{formatInvoiceStatus(invoice.status)}</p>
            <p className="text-xl">{MoneyFormatter.format(invoice.total)}</p>
            <p>Vencimento {formatDate(invoice.dueDate)}</p>
          </div>
        </header>

        <div className="h-32"></div>
      </div>

      {invoice.transactions === undefined &&
        <p className='text-gray-500 dark:text-gray-300 text-center'>Sem transações encontradas</p>
      }

      {invoice.transactions?.map(transaction => {
        return (
          <div className="block w-full p-3" key={transaction.id}>
            <InvoiceTransactionCard transaction={transaction} key={transaction.id} />
          </div>
        )
      })
      }

    </Container>
  )
}