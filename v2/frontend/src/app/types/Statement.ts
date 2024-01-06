import { Credit } from "./Credit";
import { Debit } from "./Debit";

export interface Statement {
    id: string;
    title: string;
    moment: string;
    amount: number;
    description: string;
    type: StatementType;
    method: string;
    financialMovement: FinancialMovementType;
    transactionId: string;
    debit: Debit;
    credit: Credit;
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