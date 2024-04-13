export interface Invoice {
    id: string;
    dueDate: string;
    closingDate: string;
    total: number;
    status: 'OPEN' | 'CLOSED' | 'NONE' | 'OVERDUE' | 'PAID';
    transactions?: InvoiceTransaction[]
}

export interface InvoiceTransaction {
    id: string;
    value: number;
    description: string;
    occuredOn: string;
    type: string;
    planId?: string,
    status?: string
}
