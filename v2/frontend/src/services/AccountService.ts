import { AccountResponse, FindAccountByType } from "@/types/Account";
import { get, post } from "./ApiRequest";
import { getToken } from "@/app/api/auth/[...nextauth]/options";

export async function getAccount(token: string): Promise<AccountResponse> {
    return await get('/api/v1/internal/accounts/authenticated', token);
}

export async function findAccountByType(type: 'account' | 'contact' | 'pix', value: string, token: string): Promise<FindAccountByType> {
    return await get('/api/v1/internal/accounts', token, { type: type, value: value });
}

export async function getAuthAccount(): Promise<AccountResponse | null> {
    try {
        return await getAccount(await getToken())
    } catch (err) {
        return null
    }
}

export async function deposit(amount: number | string, token: string): Promise<any> {
    return await post('/api/v1/internal/accounts/deposit', { amount: amount }, token);
}
