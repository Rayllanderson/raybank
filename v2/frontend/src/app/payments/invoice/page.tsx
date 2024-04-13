import { findCurrentInvoice } from '@/services/InvoiceService';
import InvoicePaymentForm from './InvoicePaymentForm';

export default async function page() {

    const currentInvoice = await findCurrentInvoice()


    return (
        <InvoicePaymentForm invoice={currentInvoice}/>
    )
}

