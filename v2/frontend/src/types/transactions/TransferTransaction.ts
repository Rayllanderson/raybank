export interface TransferTransaction {
    beneficiary: string | number| null;
    beneficiaryName: string | null;
    beneficiaryType: 'account' | 'pix' | 'contact' | null
    amount: number;
    message: string | null;
    success: boolean
}