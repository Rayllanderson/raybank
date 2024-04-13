export interface InstallmentPlan {
    id: string;
    transactionId: string;
    originalInvoiceId: string;
    establishmentId: string;
    installmentCount: number;
    installmentValue: number;
    total: number;
    refunded: string | null;
    description: string;
    createdAt: string;
    installments: Installment[];
}

export interface Installment {
    id: string;
    description: string;
    value: number;
    valueToPay: number;
    status: 'PAID' | 'OPEN' | 'OVERDUE'| 'REFUNDED' | 'CANCELED';
    dueDate: string;
    invoiceId: string;
}

export function getOrdenedInstallments(installments: Installment[]): Installment[] {
    const sortedInstallments = installments.map(installment => ({
        ...installment,
        dueDate: new Date(installment.dueDate) 
    }));

    sortedInstallments.sort((a, b) => a.dueDate.getTime() - b.dueDate.getTime());

    return sortedInstallments.map(installment => ({
        ...installment,
        dueDate: installment.dueDate.toISOString()
    }));
}