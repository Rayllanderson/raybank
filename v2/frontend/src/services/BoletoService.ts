import { Boleto, GenerateBoletoRequest } from "@/types/Boleto";
import { snakeToCamel } from "@/utils/StringUtils";
import { get, post } from "./ApiRequest";
import { getToken } from "@/app/api/auth/[...nextauth]/options";

export async function findAllBoletos(): Promise<Boleto[]> {
    return await findAllBoletosUsingToken(await getToken())
}

export async function findBoletoByBarCode(code: string): Promise<Boleto[]> {
    return await findBoletoByBarCodeUsingToken(code, await getToken())
}

export async function findBoletoByBarCodeUsingToken(code: string, token: string): Promise<Boleto[]> {
    const response = await get(`/api/v1/internal/boletos/${code}`, token);
    return snakeToCamel(response)
}

export async function findAllBoletosUsingToken(token: string): Promise<Boleto[]> {
    const response = await get(`/api/v1/internal/boletos`, token);
    return snakeToCamel(response)
}

export type DepositBoletoResponse = { barCode: string, value: string }
export async function generateBoleto(data: GenerateBoletoRequest, token: string): Promise<DepositBoletoResponse> {
    const response = await post(`/api/v1/internal/boletos`, data, token);
    return snakeToCamel(response)
}

export async function payBoleto(barCode: string, token: string): Promise<any> {
    const data = { bar_code: barCode }
    const response = await post(`/api/v1/internal/boletos/payment`, data, token);
    return snakeToCamel(response)
}
