import { getServerAuthSession } from "@/app/api/auth/[...nextauth]/options";
import Session from "@/types/Session";

export type LoginResponse = {
    access_token: string;
}

export const keycloak = {
    login, logout
}

async function logout() {
    try {
      await fetch(`/api/auth/logout`, { method: "GET" });
    } catch (err) {
      console.error(err);
    }
  }

async function login(username: string, password: string): Promise<LoginResponse> {
    const url = `${process.env.KEYCLOAK_ISSUER_URL}/protocol/openid-connect/token`;
    const session: Session = await getServerAuthSession()
    const token = session.token
    
    const formData = new URLSearchParams();
    formData.append('username', username);
    formData.append('password', password);
    formData.append('grant_type', 'password');
    formData.append('client_id', process.env.KEYCLOAK_CLIENT_ID!);

    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: formData
    });

    return await response.json() as LoginResponse;
}
