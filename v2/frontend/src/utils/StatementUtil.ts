import { FinancialMovementType, Statement } from "@/types/Statement";

export function isIncomming(financialMovementType: string): boolean {
    return financialMovementType === 'CREDIT'
}

export function hasDescription(statement: Statement): boolean {
    return statement.method.toString() !== 'ACCOUNT'
}