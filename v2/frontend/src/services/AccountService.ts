import { Account, AccountResponse } from "@/types/Account";
import { get } from "./ApiRequest";
import { getServerAuthSession } from "@/app/api/auth/[...nextauth]/options";
import { Session } from "next-auth";

export async function getAccount(token: string): Promise<AccountResponse> {
    return await get('/api/v1/internal/accounts/authenticated', token);
}

export async function getAuthAccount(): Promise<AccountResponse> {
    const session: Session = await getServerAuthSession()
    console.log(session)
    return await getAccount(session.token)
}

