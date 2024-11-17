import { Invoice } from "@/types/Invoice";

export const formatInvoiceStatus = (status: 'OPEN' | 'CLOSED' | 'NONE' | 'OVERDUE' | 'PAID'): string => {
    switch (status) {
        case 'OPEN':
            return 'Fatura Atual';
        case 'CLOSED':
            return 'Fatura Fechada';
        case 'NONE':
            return 'Fatura Futura';
        case 'OVERDUE':
            return 'Fatura Vencida';
        case "PAID":
            return 'Fatura Paga';
        default:
            return status
    }
}

export const getBgColor = (invoice: Invoice) => {
    if (invoice.status === 'OPEN')
        return 'bg-[#9B3BDA]'
    if (invoice.status === 'CLOSED')
        return 'bg-orange-600'
    if (invoice.status === 'PAID')
        return 'bg-green-600'
    if (invoice.status === 'OVERDUE')
        return 'bg-red-600'
    if (invoice.status === 'NONE') {
        return 'bg-indigo-600'
    }
    else
        return 'bg-indigo-500'
}