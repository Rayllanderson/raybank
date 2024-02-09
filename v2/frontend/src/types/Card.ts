import { z } from "zod";

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

export function convertCardDetailsToCamelCase(data: any): CardDetails {
    if (!data) {
        return data
    }
    const camelCaseData: CardDetails = {
        id: data.id,
        limit: data.limit,
        availableLimit: data.available_limit,
        usedLimit: data.used_limit,
        invoiceValue: data.invoice_value
    };
    return camelCaseData;
}

export const DUE_DAYS = {
    SIX: 6,
    TWELVE: 12,
    SEVENTEEN: 17,
    TWENTY_FOUR: 24,
} as const;

export const CARD_MIN_LIMIT = 0
export const CARD_MAX_LIMIT = 50000


export const createCardFormSchema = z.object({
    due_day: z.nativeEnum(DUE_DAYS),
    limit: z.number().min(CARD_MIN_LIMIT).max(CARD_MAX_LIMIT)
})

export type CreateCardFormData = z.infer<typeof createCardFormSchema>