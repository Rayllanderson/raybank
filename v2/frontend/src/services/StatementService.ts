import { Statement, Statements } from "@/types/Statement";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { ApiQueryParameters, get } from "./ApiRequest";
import { getToken } from "@/app/api/auth/[...nextauth]/options";
import { Page } from "@/types/Page";


export async function getAllCardStatementsWithToken(token: string, queryParam?: ApiQueryParameters): Promise<Page<Statement>> {
    const accountId = getAccountIdFromToken(token);
    return await get(`/api/v1/internal/accounts/${accountId}/statements`, token, queryParam);
}

export async function getAllCardStatements(): Promise<Page<Statement>> {
    return getAllCardStatementsWithToken(await getToken());
}