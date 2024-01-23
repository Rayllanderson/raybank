import { Container } from '@/components/Container'
import Separator from '@/components/Separator'
import { MoneyFormatter } from '@/utils/MoneyFormatter'
import React from 'react'
import { FaAngleRight } from 'react-icons/fa6'
import InvoiceCard from '../../../components/InvoiceCard'
import { invoices } from './mock'

export default function InvoiceList() {
    return (
        <Container >
            <h1 className="font-semibold text-xl mb-1">Resumo de faturas</h1>

            <div>
                {
                    invoices.map(invoice => {
                        return <InvoiceCard invoice={invoice} key={invoice.id} />
                    })
                }
            </div>
        </Container>
    )
}
