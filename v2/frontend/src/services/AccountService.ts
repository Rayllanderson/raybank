import { Account, AccountResponse } from "@/types/User";
import { get } from "./ApiRequest";
import { getServerAuthSession } from "@/app/api/auth/[...nextauth]/options";
import Session from "@/types/Session";

export async function getAccount(token: string): Promise<AccountResponse> {
    return await get('/api/v1/internal/accounts/authenticated', token);
}

export async function getAccountPassingToken(): Promise<AccountResponse> {
    const session: Session = await getServerAuthSession()
    return await getAccount(session.token)
}

