export const BoletoFormatter = {
    formatBarCode
}

function formatBarCode(barCode: string | undefined | null): string {
    if (barCode === null || barCode === undefined) {
        return ''
    }
    return [
        barCode.substring(0, 5),
        barCode.substring(5, 15),
        barCode.substring(15, 25),
        barCode.substring(25, 26),
        barCode.substring(26, 36),
        barCode.substring(36, 37),
        barCode.substring(37, 47),
        barCode.substring(47, 48),
        barCode.substring(48, 58),
        barCode.substring(58, 59),
    ].join(' ')
}