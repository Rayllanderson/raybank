import { AccountResponse, FindAccountByType } from "@/types/Account";
import { get, post } from "./ApiRequest";
import { getToken } from "@/app/api/auth/[...nextauth]/options";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { ProfilePicture, profilePictureDefault } from "@/types/ProfilePicture";
import { snakeToCamel } from "@/utils/StringUtils";

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

export async function getProfilePicture(): Promise<ProfilePicture> {
    try {
        const token = await getToken();
        const accountId = getAccountIdFromToken(token);
        const response = await get(`/api/v1/internal/accounts/${accountId}/profile-picture`, token);
        return snakeToCamel(response)
    } catch (err: any) {
        if (err.httpStatus === 409) {
            return generateNewPreSignUrl()
        }
        return profilePictureDefault
    }
}

async function generateNewPreSignUrl(): Promise<ProfilePicture> {
    try {
        const token = await getToken();
        const accountId = getAccountIdFromToken(token);
        const response = await post(`/api/v1/internal/accounts/${accountId}/renew-profile-picture`, null, token);
        return snakeToCamel(response)
    } catch (err) {
        return profilePictureDefault
    }
}
