import Separator from '@/components/Separator'
import { Invoice } from '@/types/Invoice'
import { formartToMonthYear } from '@/utils/DateFormatter'
import { formatInvoiceStatus } from '@/utils/InvoiceUtil'
import { MoneyFormatter } from '@/utils/MoneyFormatter'
import Link from 'next/link'
import React from 'react'
import { FaAngleRight } from 'react-icons/fa6'

interface Props {
    invoice: Invoice
}

export default function InvoiceCard({ invoice }: Props) {
    return (
        <div className='hover:scale-[1.02] transition-transform'>
            <Link href={`invoices/${invoice.id}`}>
                <div className="flex justify-between p-1 ">
                    <div className='flex flex-col space-y-1'>
                        <p className='font-semibold'>{formartToMonthYear(invoice.closingDate)}</p>
                        <p className='font-light font-sm'>{formatInvoiceStatus(invoice.status)}</p>
                    </div>

                    <div className='flex items-center space-x-2'>
                        <p>{MoneyFormatter.format(invoice.total)}</p>
                        <FaAngleRight className='h-3 w-3' />
                    </div>
                </div>
                <Separator type='thin' />
            </Link>
        </div>
    )
}
