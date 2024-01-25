import { titlerize } from "./StringUtils";

export const formatDate = (inputDate: string): string => {
    const currentDate = new Date();
    const inputDateTime = new Date(inputDate);

    const isSameDay =
        currentDate.getDate() === inputDateTime.getDate() &&
        currentDate.getMonth() === inputDateTime.getMonth() &&
        currentDate.getFullYear() === inputDateTime.getFullYear();

    const isYesterday =
        currentDate.getDate() - 1 === inputDateTime.getDate() &&
        currentDate.getMonth() === inputDateTime.getMonth() &&
        currentDate.getFullYear() === inputDateTime.getFullYear();

    const isSameYear = currentDate.getFullYear() === inputDateTime.getFullYear()

    if (isSameDay) {
        return 'Hoje';
    } else if (isYesterday) {
        return 'Ontem';
    } else {
        const day = inputDateTime.getDate();
        const month = inputDateTime.toLocaleString('PT-BR', { month: 'short' }).toUpperCase();
        const year = inputDateTime.getFullYear();

        return `${day} ${month} ${isSameYear ? '' : year}`.replace(".", "");
    }
};

export const formartToMonthYear = (inputDate: string): string => {
    const inputDateTime = new Date(inputDate);
    const month = inputDateTime.toLocaleString('PT-BR', { month: 'short' }).toUpperCase();
    const year = inputDateTime.getFullYear();
    return `${month} ${year}`.replace(".", "");
}

export const formartToDayMonth = (inputDate: string): string => {
    const inputDateTime = new Date(inputDate);
    const month = inputDateTime.toLocaleString('PT-BR', { month: 'short' }).toUpperCase();
    const day = inputDateTime.getDate()
    return `${day} ${month}`.replace(".", "");
}

export function formatLongDate(dateString: string): string {
    const date = new Date(dateString);
    const options: Intl.DateTimeFormatOptions = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
    let formattedDate = date.toLocaleDateString('pt-BR', options)
    formattedDate = formattedDate.replace(' às', ',')
    formattedDate = titlerize(formattedDate)
    formattedDate = formattedDate.replace(/\b(De)\b/g, (match) => match.toLowerCase());
    return formattedDate
  }
  