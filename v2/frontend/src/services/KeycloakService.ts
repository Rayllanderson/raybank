export type LoginResponse = {
    access_token: string;
}

export const keycloak = {
    login
}

async function login(username: string, password: string): Promise<LoginResponse> {
    const url = `${process.env.KEYCLOAK_ISSUER_URL}/protocol/openid-connect/token`;
    
    const formData = new URLSearchParams();
    formData.append('username', username);
    formData.append('password', password);
    formData.append('grant_type', 'password');
    formData.append('client_id', process.env.KEYCLOAK_CLIENT_ID!);

    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    });

    return await response.json() as LoginResponse;
}
