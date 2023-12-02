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