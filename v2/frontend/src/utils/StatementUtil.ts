import { FinancialMovementType } from "@/types/Statement";

export function isIncomming(financialMovementType: FinancialMovementType): boolean {
    return financialMovementType === FinancialMovementType.CREDIT
}