export const formatInvoiceStatus = (status: 'OPEN' | 'CLOSED' | 'NONE' | 'OVERDUE'): string => {
    switch (status) {
        case 'OPEN':
            return 'Fatura Atual';
        case 'CLOSED':
            return 'Fatura Fechada';
        case 'NONE':
            return 'Fatura Futura';
        case 'OVERDUE':
            return 'Fatura Vencida';
        default:
            return status
    }
}