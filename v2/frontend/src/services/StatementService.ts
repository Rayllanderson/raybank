import { Statement, Statements, convertStatementToSnakeToCamel } from "@/types/Statement";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { ApiQueryParameters, get } from "./ApiRequest";
import { getToken } from "@/app/api/auth/[...nextauth]/options";
import { Page } from "@/types/Page";


export async function getAllCardStatementsWithToken(token: string, queryParam?: ApiQueryParameters): Promise<Page<Statement>> {
    const accountId = getAccountIdFromToken(token);
    const page:Page<Statement> = await get(`/api/v1/internal/accounts/${accountId}/statements`, token, queryParam);
    const itemsSnakeCase = page.items.map((item: Statement) => convertStatementToSnakeToCamel(item));
    return { ...page, items: itemsSnakeCase };
}

export async function getAllCardStatements(): Promise<Page<Statement>> {
    return getAllCardStatementsWithToken(await getToken());
}