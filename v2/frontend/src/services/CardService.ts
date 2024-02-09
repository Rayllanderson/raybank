import { get, post } from "./ApiRequest";
import { getToken } from "@/app/api/auth/[...nextauth]/options";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { CardDetails, CreateCardFormData, convertCardDetailsToCamelCase } from "@/types/Card";
import { ApiErrorException } from "@/types/Error";

export const CardService = {createCard}

export async function getCardByIdAndToken(token: string): Promise<CardDetails> {
    const accountId = getAccountIdFromToken(token);
    const response = await get(`/api/v1/internal/accounts/${accountId}/cards`, token);
    return convertCardDetailsToCamelCase(response)
}

export async function getCreditCard(): Promise<CardDetails> {
    return await getCardByIdAndToken(await getToken())
}

export async function getCreditCardOrNullIfNotFound(): Promise<CardDetails | null> {
    try {
        return await getCardByIdAndToken(await getToken())
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