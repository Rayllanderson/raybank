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