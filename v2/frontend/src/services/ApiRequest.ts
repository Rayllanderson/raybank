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
    headers: {[key: string]: string},
    method: string
): Promise<T> {
    const queryString: string = buildQueryString({ ...query });
    
    const defaultHeaders = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
        'Accept': '*/*',
        'Access-Control-Allow-Origin': '*',
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X)',
        'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
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
        console.log(`${API_URL}${endpoint}${queryString}`)
        console.log(requestOptions)
        const response = await fetch(`${API_URL}${endpoint}${queryString}`, requestOptions);
        console.log(response)
        if (!response.ok) {
            console.log(response)
            console.log(response.status)
            throw new ApiErrorException(response.status, await response.json() as ApiError)
        }

        return await response.json();
    } catch (error) {
        console.log(error)
        throw error;
    }
}

export async function get<T>(
    endpoint: string,
    token: string,
    query: ApiQueryParameters = {},
    headers: {[key: string]: string} = {},
): Promise<T> {
    return apiRequest(endpoint, null, token, query, headers, 'GET')
}

export async function post<T>(
    endpoint: string,
    body: any,
    token: string,
    query: ApiQueryParameters = {},
    headers: {[key: string]: string} = {},
): Promise<T> {
    return apiRequest(endpoint, body, token, query, headers, 'POST')
}