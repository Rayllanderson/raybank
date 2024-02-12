import { Account, AccountResponse, FindAccountByType } from "@/types/Account";
import { get } from "./ApiRequest";
import { getServerAuthSession, getToken } from "@/app/api/auth/[...nextauth]/options";
import { Session } from "next-auth";
import { ApiErrorException } from "@/types/Error";

export async function getAccount(token: string): Promise<AccountResponse> {
    return await get('/api/v1/internal/accounts/authenticated', token);
}

export async function findAccountByType(type: 'account' | 'contact' | 'pix', value: string, token: string): Promise<FindAccountByType> {
    return await get('/api/v1/internal/accounts', token, { type: type, value: value });
}

export async function getAuthAccount(): Promise<AccountResponse> {
    return await getAccount(await getToken())
}

