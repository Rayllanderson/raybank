import { TransferTransaction } from "@/types/transactions/TransferTransaction";
import { post } from "./ApiRequest";

export const TransferSerice = {
transfer
}

export async function transfer(transaction: TransferTransaction, token: string) {
    const url = transaction.beneficiaryType === 'pix' ? '/api/v1/internal/pix/transfer' : '/api/v1/internal/accounts/transfer'

    const body = {
        amount: transaction.amount,
        message: transaction.message,
        credit_key: transaction.beneficiary,
        beneficiary_account_number: transaction.beneficiary
    }

    transaction.beneficiaryType === 'pix' ? body.beneficiary_account_number = null : body.credit_key = null

    await post(url, body, token);
}