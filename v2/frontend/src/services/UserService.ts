import { Account, AccountResponse } from "@/types/User";
import { User } from "next-auth";
import { LoginResponse, keycloak } from "./KeycloakService";

export const userService = {
    authenticate: login,
};

async function login(username: string, password: string) {
    const token: LoginResponse = await keycloak.login(username, password);

    if (token) {
        const accountData = await geUserAccount(token.access_token);

        if (accountData) {
            return {
                name: accountData.user.name,
                account: accountData.account,
                token: token.access_token
            };
        }
    }
    return null
}

export async function geUserAccount(token: string): Promise<AccountResponse> {
    const url = `${process.env.API_URL}/api/v1/internal/accounts/authenticated`;
    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });
    return await response.json();
}
