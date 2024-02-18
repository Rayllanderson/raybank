import Separator from '@/components/Separator'
import { Invoice } from '@/types/Invoice'
import { formartToMonthYear } from '@/utils/DateFormatter'
import { formatInvoiceStatus } from '@/utils/InvoiceUtil'
import { MoneyFormatter } from '@/utils/MoneyFormatter'
import Link from 'next/link'
import React from 'react'
import { FaAngleRight } from 'react-icons/fa6'
import LoadingDiv from '../LoadingDiv'

export default function InvoiceCardLoading() {
    return (
        <div className='hover:scale-[1.02] transition-transform'>
            <div className="flex justify-between p-1 ">
                <div className='flex flex-col space-y-1 w-full'>
                    <div className='w-20'>
                        <LoadingDiv className='rounded-md' />
                    </div>

                    <div className='w-14'>
                        <LoadingDiv className='rounded-md' />
                    </div>
                </div>

                <div className='flex items-center space-x-2 '>
                    <div className='w-10 '>
                        <LoadingDiv className='rounded-md' />
                    </div>
                    <FaAngleRight className='h-3 w-3' />
                </div>
            </div>
            <Separator type='thin' />
        </div>
    )
}
