import { createPurchase } from '@/services/CardService'
import { createPurchaseFormSchema } from '@/types/Card'
import { ApiErrorException } from '@/types/Error'
import { CognitoIdentityProviderClient, InitiateAuthCommand, InitiateAuthCommandInput } from "@aws-sdk/client-cognito-identity-provider";

const client = new CognitoIdentityProviderClient({ region: "us-east-1" });

export async function POST(
    req: Request,
) {
    const requestBody = createPurchaseFormSchema.parse(await req.json())
    const establishmentToken = await getEstablishmentToken()

    try {
        const response = await createPurchase(establishmentToken, requestBody)

        return new Response(JSON.stringify(response), { status: 200 })
    } catch (error) {
        if (error instanceof ApiErrorException) {
            return new Response(JSON.stringify({ message: error.message }), { status: error.httpStatus });
        } else {
            console.error("Erro inesperado:", error);
            return new Response(JSON.stringify({ message: "Erro interno" }), { status: 500 });
        }
    }
}


async function getEstablishmentToken() {
    if (process.env.PROVIDER === 'cognito') {

        const params:InitiateAuthCommandInput = {
            AuthFlow: 'USER_PASSWORD_AUTH',
            ClientId: process.env.PROVIDER_PURCHASE_CLIENT_ID || '',
            AuthParameters: {
                USERNAME: process.env.PROVIDER_PURCHASE_USERNAME || '',
                PASSWORD: process.env.PROVIDER_PURCHASE_PASSWORD || '',
            },
        };

        try {
            const command = new InitiateAuthCommand(params);
            const response = await client.send(command);
            return response.AuthenticationResult!.AccessToken;
        } catch (err) {
            console.error("Authentication error:", err);
        }
    } else {
        const url = new URL(`${process.env.KEYCLOAK_ISSUER}/protocol/openid-connect/token`);
        const body = new URLSearchParams({
            username: process.env.KEYCLOAK_ESTABLISHMENT_USERNAME || '',
            password: process.env.KEYCLOAK_ESTABLISHMENT_PASSWORD || '',
            grant_type: 'password',
            client_id: process.env.KEYCLOAK_ESTABLISHMENT_CLIENT_ID || '',
        });

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'User-Agent': 'Raybank',
            },
            body,
        });

        if (!response.ok) {
            throw new Error(
                `Falha na autenticação: ${response.status} - ${await response.text()}`
            );
        }

        const data = await response.json();
        return data.access_token;
    }
}
