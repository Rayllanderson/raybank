export const MoneyFormatter = new Intl.NumberFormat('pt-br', { style: 'currency', currency: 'BRL' })

export default function formatMoney(value: string | number) {
    if (isNumber(value)) {
        return MoneyFormatter.format(value as number)
    }
    return value
}

function isNumber(value: string | number): boolean {
    return !isNaN(value as number);
}

export function getValueNumberFromMoneyInput(value: any): number {
    const rawValue = value.replace(/[^\d]/g, '');
    return parseFloat(rawValue) / 100 || 0;
}