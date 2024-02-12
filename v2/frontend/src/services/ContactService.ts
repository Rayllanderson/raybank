import { Contact } from "@/types/Contact";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { snakeToCamel } from "@/utils/StringUtils";
import { get } from "./ApiRequest";
import { getToken } from "@/app/api/auth/[...nextauth]/options";

export async function findAllContactsUsingToken(token: string): Promise<Contact[]> {
    const accountId = getAccountIdFromToken(token);
    const response = await get(`/api/v1/internal/accounts/${accountId}/contacts`, token);
    return snakeToCamel(response)
}

export async function findAllContacts(): Promise<Contact[]> {
    return await findAllContactsUsingToken(await getToken());
}