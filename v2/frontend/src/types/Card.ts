export interface Card {
    id:     string;
    status: string;
}

export interface CardDetails {
    id:string
    limit: number
    availableLimit: number
    usedLimit: number
    invoiceValue?: number
}
