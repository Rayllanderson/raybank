import { get, patch, post } from "./ApiRequest";
import { getToken } from "@/app/api/auth/[...nextauth]/options";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { CardDetails, ChangeCardLimitFormData, CreateCardFormData, CreatePurchaseFormData, convertCardDetailsToCamelCase } from "@/types/Card";
import { ApiErrorException } from "@/types/Error";

export const CardService = {createCard, changeLimit}

export async function getCardByIdAndToken(token: string, sensitive: boolean = false): Promise<CardDetails> {
    const accountId = getAccountIdFromToken(token);
    const response = await get(`/api/v1/internal/accounts/${accountId}/cards`, token, {sensitive: sensitive});
    return convertCardDetailsToCamelCase(response)
}

export async function getCreditCard(sensitive: boolean = false): Promise<CardDetails> {
    return await getCardByIdAndToken(await getToken(), sensitive)
}

export async function getCreditCardWithSensitiveData(token: string): Promise<CardDetails> {
    return await getCardByIdAndToken(token, true)
}

export async function getCreditCardOrNullIfNotFound(sensitive: boolean = false): Promise<CardDetails | null> {
    try {
        return await getCardByIdAndToken(await getToken(), sensitive)
    } catch (err) {
        if (err instanceof ApiErrorException) {
            if (err.httpStatus === 404) {
                return null
            }
        }
        throw err
    }
}

export async function createCard(body: CreateCardFormData, token: string) {
    const accountId = getAccountIdFromToken(token);
    return post(`/api/v1/internal/accounts/${accountId}/cards`, body, token);
}

export async function changeLimit(body: ChangeCardLimitFormData, token: string) {
    const accountId = getAccountIdFromToken(token);
    return patch(`/api/v1/internal/accounts/${accountId}/cards/limit`, body, token);
}


export async function createPurchase(body: CreatePurchaseFormData) {
    console.log(body);
    return body
}