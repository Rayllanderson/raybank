import { FinancialMovementType, Statement } from "@/types/Statement";

export function isIncomming(financialMovementType: string): boolean {
    return financialMovementType === 'CREDIT'
}

export function hasDescription(statement: Statement): boolean {
    return statement.method.toString() !== 'ACCOUNT'
}

export function formatStatementMethod(method: string): string {
    switch (method) {
        case 'PIX':
            return 'PIX';
        case 'ACCOUNT':
            return 'Conta Bancária';
        case 'DEBIT_CARD':
            return 'Cartão de Débito';
        case 'INVOICE':
            return 'Fatura';
        case 'CREDIT_CARD':
            return 'Cartão de Crédito';
        case 'BOLETO':
            return 'Boleto Bancário';
        case 'RAYBANK_TRANSFER':
            return 'Transferência RayBank';
        case 'ACCOUNT_TRANSFER':
            return 'Transferência Bancária';
        default:
            return '';
    }
}