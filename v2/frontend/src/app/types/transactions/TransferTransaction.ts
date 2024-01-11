export interface TransferTransaction {
    beneficiaryAccountNumber: number | null;
    amount: number;
    message: string | null;
} 