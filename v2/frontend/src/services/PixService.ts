import { Pix, PixKey, QrCode } from "@/types/Pix";
import { snakeToCamel } from "@/utils/StringUtils";
import { doDelete, get, patch, post } from "./ApiRequest";
import { getToken } from "@/app/api/auth/[...nextauth]/options";
import { RegisterPixKeySchemaData } from "@/components/form/RegisterPixKeyForm";

export const PixService = {
    findAllPixKey, register, deleteByKey, findLimit, updateLimit, doReturn, findByE2E, generateQrCode,findByQrCode: findQrCode,payQrCode
}

export async function findAllPixKeyUsingToken(token: string): Promise<PixKey[]> {
    const response = await get(`/api/v1/internal/pix/keys`, token);
    return snakeToCamel(response).keys
}

export async function register(body:RegisterPixKeySchemaData, token: string): Promise<PixKey> {
    const response = await post(`/api/v1/internal/pix/keys`, body, token);
    return snakeToCamel(response)
}

export async function deleteByKey(key: string, token: string): Promise<any> {
    const response = await doDelete(`/api/v1/internal/pix/keys/${key}`, token);
    return snakeToCamel(response)
}

export async function findAllPixKey(): Promise<PixKey[]> {
    return findAllPixKeyUsingToken(await getToken());
}

export async function findLimit(token: string): Promise<{ limit: number }> {
    const response = await get(`/api/v1/internal/pix/limits`, token);
    return snakeToCamel(response)
}

export async function updateLimit(newLimit: number, token: string): Promise<{ newLimit: number }> {
    const response = await patch(`/api/v1/internal/pix/limits`, { new_limit: newLimit }, token);
    return snakeToCamel(response)
}

export async function findByE2E(e2e: string, token: string): Promise<Pix> {
    const response = await get(`/api/v1/external/pix/${e2e}`, token);
    return snakeToCamel(response)
}

export async function doReturn(amount: number, e2eId: string, message: string | null = null, token: string): Promise<Pix> {
    const response = await post(`/api/v1/internal/pix/return`, {
        amount: amount,
        pix_id: e2eId,
        message: message
    }, token);
    return snakeToCamel(response)
}

export async function generateQrCode(amount: number | string, pixKey: string, description: string | null = null, token: string): Promise<{
    id: string,
    code: string,
    amount: number,
    description?: string
}> {
    const request = { amount: amount, description: description, credit_key: pixKey }
    return await post('/api/v1/internal/pix/qrcode', request, token);
}

export async function findQrCode(idOrQrCode: string, token: string): Promise<QrCode> {
    const response = await get(`/api/v1/internal/pix/qrcode/${idOrQrCode}`, token)
    return snakeToCamel(response)
}

export async function payQrCode(qrCode:string, token: string): Promise<Pix> {
    const response = await post(`/api/v1/internal/pix/payment`, {qr_code: qrCode}, token);
    return snakeToCamel(response)
}
