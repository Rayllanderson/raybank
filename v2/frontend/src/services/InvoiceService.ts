import { Invoice } from "@/types/Invoice";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { get } from "./ApiRequest";
import { snakeToCamel } from "@/utils/StringUtils";
import { getToken } from "@/app/api/auth/[...nextauth]/options";
import { ApiErrorException } from "@/types/Error";

export async function findAllInvoicesUsingToken(token: string): Promise<Invoice[]> {
    const accountId = getAccountIdFromToken(token);
    const response = await get(`/api/v1/internal/accounts/${accountId}/invoices`, token);
    return snakeToCamel(response)
}

export async function findInvoiceByIdUsingToken(id: string, token: string): Promise<Invoice> {
    const accountId = getAccountIdFromToken(token);
    const response = await get(`/api/v1/internal/accounts/${accountId}/invoices/${id}`, token);
    return snakeToCamel(response)
}

export async function findAllInvoices(): Promise<Invoice[]> {
    return await findAllInvoicesUsingToken(await getToken());
}

export async function findInvoiceById(id: string): Promise<Invoice> {
    return await findInvoiceByIdUsingToken(id, await getToken());
}

export async function findInvoiceOrNull(id: string): Promise<Invoice | null> {
    try {
        return await findInvoiceByIdUsingToken(id, await getToken());
    } catch (err) {
        console.log(err)
        if (err instanceof ApiErrorException) {
            if (err.httpStatus === 404) {
                return null
            }
        }
        throw err;
    }
}