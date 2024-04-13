import { findCurrentInvoice, findInvoiceById } from '@/services/InvoiceService';
import InvoicePaymentForm from '../InvoicePaymentForm';

export default async function page({params}: {params:{id:string}}) {

    const invoiceToPay = await findInvoiceById(params.id)


    return (
        <InvoicePaymentForm invoice={invoiceToPay}/>
    )
}

