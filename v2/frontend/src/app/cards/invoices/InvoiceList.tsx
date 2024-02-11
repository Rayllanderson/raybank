import { Container } from '@/components/Container'
import React from 'react'
import InvoiceCard from '../../../components/InvoiceCard'
import { findAllInvoices } from '@/services/InvoiceService'

export default async function InvoiceList() {

    const invoices = await findAllInvoices()

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
