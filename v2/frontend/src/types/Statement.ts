import { Credit } from "./Credit";
import { Debit } from "./Debit";

export interface Statement {
    id: string;
    title: string | null;
    moment: string;
    amount: number;
    description: string;
    type: StatementType;
    method: string;
    financialMovement: FinancialMovementType;
    transactionId: string;
    debit: Debit;
    credit: Credit;
    installmentPlan?: InstallmentPlan
}

export interface InstallmentPlan {
    id: string;
    installments: number
}

export enum StatementType {
    TRANSFER, DEPOSIT, PAYMENT, REFUND, RETURN
}

export enum StatementMethod {
    PIX, ACCOUNT, DEBIT_CARD, INVOICE, CREDIT_CARD, BOLETO, RAYBANK_TRANSFER, ACCOUNT_TRANSFER
}

export enum FinancialMovementType {
    DEBIT, CREDIT
}

export type Statements = Statement[]


export function convertStatementToSnakeToCamel(obj: any): any {
    if (typeof obj !== 'object' || obj === null) {
        return obj;
    }

    if (Array.isArray(obj)) {
        return obj.map(item => convertStatementToSnakeToCamel(item));
    }

    const newObj: any = {};
    for (const key in obj) {
        if (Object.prototype.hasOwnProperty.call(obj, key)) {
            const newKey = key.replace(/_([a-z])/g, (_, letter) => letter.toUpperCase());
            newObj[newKey] = convertStatementToSnakeToCamel(obj[key]);
        }
    }
    return newObj;
}