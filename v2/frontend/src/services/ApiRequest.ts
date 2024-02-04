const API_URL = process.env.API_URL;

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
    token: string,
    query: ApiQueryParameters = {},
    headers: {[key: string]: string} = {},
    method: string = 'GET'
): Promise<T> {
    const queryString: string = buildQueryString({ ...query });
    
    const defaultHeaders = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    };

    try {
        const response = await fetch(`${API_URL}${endpoint}${queryString}`, {
            method: method,
            headers: {
                ...defaultHeaders,
                ...headers
            }
        });

        if (!response.ok) {
            throw new Error(`API request failed: ${response.statusText}`);
        }

        return await response.json();
    } catch (error) {
        throw error;
    }
}

export async function get<T>(
    endpoint: string,
    token: string,
    query: ApiQueryParameters = {},
    headers: {[key: string]: string} = {},
): Promise<T> {
    return apiRequest(endpoint, token, query, headers, 'GET')
}

export async function post<T>(
    endpoint: string,
    token: string,
    query: ApiQueryParameters = {},
    headers: {[key: string]: string} = {},
): Promise<T> {
    return apiRequest(endpoint, token, query, headers, 'POST')
}