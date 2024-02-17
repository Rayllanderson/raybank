import { ApiError, ApiErrorException } from "@/types/Error";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

export interface ApiQueryParameters {
    [key: string]: string | number | boolean;
}

export function buildQueryString(params: ApiQueryParameters): string {
    const query = Object.entries(params)
        .filter(([, value]) => value !== undefined)
        .map(([key, value]) => [key, encodeURIComponent(String(value))]);

    return `?${new URLSearchParams(Object.fromEntries(query)).toString()}`;
}

export async function apiRequest<T>(
    endpoint: string,
    data: any,
    token: string,
    query: ApiQueryParameters,
    headers: { [key: string]: string },
    method: string
): Promise<T> {
    const queryString: string = decodeURIComponent(buildQueryString({ ...query }));

    const defaultHeaders = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    };

    let requestOptions: { method: string; headers: { [key: string]: string }; body?: string | null } = {
        method: method,
        headers: {
            ...defaultHeaders,
            ...headers
        }
    };

    if (data !== null) {
        requestOptions.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(`${API_URL}${endpoint}${queryString}`, requestOptions);
        const contentType = response.headers.get('content-type');

        let responseBody

        if (contentType && contentType.includes('application/json')) {
            responseBody = await response.json();
        } else {
            responseBody = null
        }

        if (!response.ok) {
            throw new ApiErrorException(response.status, await (responseBody !== null ? responseBody : { status: response.status, message: 'Ocorreu um erro' }) as ApiError)
        }

        return await responseBody
    } catch (error) {
        console.log(error)
        throw error;
    }
}

export async function get<T>(
    endpoint: string,
    token: string,
    query: ApiQueryParameters = {},
    headers: { [key: string]: string } = {},
): Promise<T> {
    return await apiRequest(endpoint, null, token, query, headers, 'GET')
}

export async function post<T>(
    endpoint: string,
    body: any,
    token: string,
    query: ApiQueryParameters = {},
    headers: { [key: string]: string } = {},
): Promise<T> {
    return await apiRequest(endpoint, body, token, query, headers, 'POST')
}

export async function doDelete<T>(
    endpoint: string,
    token: string,
    query: ApiQueryParameters = {},
    headers: { [key: string]: string } = {},
): Promise<T> {
    return await apiRequest(endpoint, null, token, query, headers, 'DELETE')
}

export async function patch<T>(
    endpoint: string,
    body: any,
    token: string,
    query: ApiQueryParameters = {},
    headers: { [key: string]: string } = {},
): Promise<T> {
    return await apiRequest(endpoint, body, token, query, headers, 'PATCH')
}