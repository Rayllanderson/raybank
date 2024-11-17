import { z } from "zod";

export interface Card {
    id: string;
    status: string;
}

export interface CardDetails {
    id: string
    limit: number
    availableLimit: number
    usedLimit: number
    invoiceValue?: number
    number?: string,
    expiryDate?: string,
    securityCode?: number
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
        invoiceValue: data.invoice_value,
        number: data.number,
        securityCode: data.security_code,
        expiryDate: data.expiry_date
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

export const changeCardLimitFormSchema = z.object({
    limit: z.number().min(CARD_MIN_LIMIT).max(CARD_MAX_LIMIT)
})


export type CreateCardFormData = z.infer<typeof createCardFormSchema>

export type ChangeCardLimitFormData = z.infer<typeof changeCardLimitFormSchema>

export type CreatePurchaseFormData = z.infer<typeof createPurchaseFormSchema>


export const createPurchaseFormSchema = z.object({
    amount: z.number().min(0.01, "Valor da compra precisa ser maior que 0"),
    payment_type: z.enum(["credit", "debit"], {
        errorMap: () => {
            return { message: "Tipo de pagamento inválido. deve ser debit ou credit" };
        }
    }),

    installments: z.number().min(1, "Número de parcelas inválido").max(12, "Máximo de 12 parcelas"),
    occurred_on: z.string().optional(),
    description: z.string(),

    card: z.object({
        number: z.string().length(16, "Número do cartão precisa ter 16 caracteres"),
        security_code: z.string().length(3, "CVV precisa ter 3 caracteres"),
        expiry_date: z.string().regex(/^(0[1-9]|1[0-2])\/([0-9]{4})$/, "Data de validade inválida (MM/AAAA)")
    })
});
