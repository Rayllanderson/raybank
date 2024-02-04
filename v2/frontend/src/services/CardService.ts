import { get } from "./ApiRequest";
import { getServerAuthSession } from "@/app/api/auth/[...nextauth]/options";
import Session from "@/types/Session";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { CardDetails } from "@/types/Card";

export async function getCard(id:string, token: string): Promise<CardDetails> {
    const accountId = getAccountIdFromToken(token);
    return await get(`/api/v1/internal/accounts${accountId}/cards/${id}`, token);
}

export async function getCardPassingToken(id:string): Promise<CardDetails> {
    const session: Session = await getServerAuthSession()
    return await getCard(id, session.token)
}

