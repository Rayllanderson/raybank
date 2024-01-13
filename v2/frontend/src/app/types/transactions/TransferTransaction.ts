export interface TransferTransaction {
    beneficiary: string | null;
    beneficiaryName: string | null;
    beneficiaryType: 'account' | 'pix' | 'contact' | null
    amount: number;
    message: string | null;
}