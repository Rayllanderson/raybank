export interface TransferTransaction {
    beneficiary: string | null;
    amount: number;
    message: string | null;
    beneficiaryType: 'account' | 'pix' | 'contact' | null
} 