import { createPurchase } from '@/services/CardService'
import { createPurchaseFormSchema } from '@/types/Card'
import { ApiErrorException } from '@/types/Error'

export async function POST(
    req: Request,
) {
    const requestBody = createPurchaseFormSchema.parse(await req.json())
    const establishmentToken = await getEstablishmentToken()

    try {
        const response = await createPurchase(establishmentToken, requestBody)

        console.log(response)

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
