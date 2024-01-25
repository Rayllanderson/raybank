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
    status: string;
    dueDate: string;
    invoiceId: string;
}