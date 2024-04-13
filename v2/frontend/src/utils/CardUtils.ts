import { titlerize } from "./StringUtils";

export function getAbbreviatedName(name: string) {
    const nameTitlerized = titlerize(name);

    const nameParts = nameTitlerized.split(' ');

    if (nameParts.length >= 2) {
        const firstName = nameParts[0];
        const lastName = nameParts[nameParts.length - 1];
        const abbreviatedName = `${firstName} ${lastName.charAt(0)}.`;
        return abbreviatedName;
    } else {
        return name;
    }
}

export function formatCardNumber(number: string): string {
    return number.replace(/(\d{4})/g, '$1 ').trim();
}

export function formatExpirationDate(expirationDate: string): string {
    const [year, month] = expirationDate.split('-');

    const formattedDate = `${month}/${year.slice(-2)}`;

    return formattedDate;
}