import { AccountResponse, FindAccountByType } from "@/types/Account";
import { doDelete, get, post, postMultiPartFile } from "./ApiRequest";
import { getToken } from "@/app/api/auth/[...nextauth]/options";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { OriginalImage } from "@/types/ProfilePicture";
import { snakeToCamel } from "@/utils/StringUtils";
import { handlerApiError } from "./HandlerApiError";

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
        console.log(err)
        return null
    }
}

export async function deposit(amount: number | string, token: string): Promise<any> {
    return await post('/api/v1/internal/accounts/deposit', { amount: amount }, token);
}
