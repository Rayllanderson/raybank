import { get } from "./ApiRequest";
import { getServerAuthSession } from "@/app/api/auth/[...nextauth]/options";
import Session from "@/types/Session";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { CardDetails } from "@/types/Card";

export async function getCardByIdAndToken(token: string): Promise<CardDetails> {
    const accountId = getAccountIdFromToken(token);
    return await get(`/api/v1/internal/accounts/${accountId}/cards`, token);
}

export async function getCreditCard(): Promise<CardDetails> {
    const session: Session = await getServerAuthSession()
    return await getCardByIdAndToken(session.token)
}

