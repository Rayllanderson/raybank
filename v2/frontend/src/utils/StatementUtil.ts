import { Statement } from "@/types/Statement";
import moment from 'moment'
import 'moment/locale/pt'

export function isIncomming(financialMovementType: string): boolean {
    return financialMovementType === 'CREDIT'
}

export function hasDescription(statement: Statement): boolean {
    return statement.method.toString() !== 'ACCOUNT'
}

export function formatStatementMoment(momento: string): string {
    return moment(momento).locale('pt-br').format('dddd, D [de] MMMM [de] YYYY, HH[h]mm');
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