export interface Invoice {
    id: string;
    dueDate: string;
    closingDate: string;
    total: number;
    status: 'OPEN' | 'CLOSED' | 'NONE' | 'OVERDUE';
}
