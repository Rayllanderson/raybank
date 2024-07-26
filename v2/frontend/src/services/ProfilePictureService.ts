import { OriginalImage, ProfilePicture } from "@/types/ProfilePicture";
import { getAccountIdFromToken } from "@/utils/JwtUtil";
import { snakeToCamel } from "@/utils/StringUtils";
import { doDelete, get, post, postMultiPartFile } from "./ApiRequest";

export async function getProfilePicture(token: string): Promise<ProfilePicture | null> {
    try {
        const accountId = getAccountIdFromToken(token);
        const response = await get(`/api/v1/internal/accounts/${accountId}/profile-picture`, token);
        return snakeToCamel(response)
    } catch (err: any) {
        if (err.httpStatus === 410) {
            return generateNewPreSignUrlClientSide(token)
        }
        return null
    }
}

async function generateNewPreSignUrlClientSide(token:string): Promise<ProfilePicture | null> {
    try {
        const accountId = getAccountIdFromToken(token);
        const response = await post(`/api/v1/internal/accounts/${accountId}/renew-profile-picture`, null, token);
        return snakeToCamel(response)
    } catch (err) {
        return null
    }
}

export async function uploadProfilePicture(body: any, token: string): Promise<OriginalImage> {
    const accountId = getAccountIdFromToken(token);
    const response = await postMultiPartFile(`/api/v1/internal/accounts/${accountId}/profile-picture`, body, token);
    return snakeToCamel(response)
}

export async function deleteProfilePicture(token: string) {
    const accountId = await getAccountIdFromToken(token);
    await doDelete(`/api/v1/internal/accounts/${accountId}/profile-picture`, token);
}