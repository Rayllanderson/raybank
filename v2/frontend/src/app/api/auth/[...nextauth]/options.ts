import Session from '@/types/Session';
import { TokenSet, getServerSession } from 'next-auth';
import KeycloakProvider from "next-auth/providers/keycloak";
import { redirect } from 'next/navigation';

const COOKIES_LIFE_TIME = 24 * 60 * 60;
const COOKIE_PREFIX = process.env.NODE_ENV === 'production' ? '__Secure-' : '';

export const authOptions = {

    providers: [
        KeycloakProvider({
            clientId: process.env.KEYCLOAK_ID!,
            clientSecret: process.env.KEYCLOAK_SECRET!,
            issuer: process.env.KEYCLOAK_ISSUER!,
        })
    ],

    callbacks: {
        async jwt({ account, token }: { account: any, token: any }) {
            if (account) {
                token.user = account
                return token
            } else if (Date.now() < token.user.expires_at * 1000) {
                return token
            } else {
                try {
                    const response = await fetch(process.env.REFRESH_TOKEN_URL!, {
                        headers: { "Content-Type": "application/x-www-form-urlencoded" },
                        body: new URLSearchParams({
                            client_id: process.env.KEYCLOAK_ID!,
                            client_secret: process.env.KEYCLOAK_SECRET!,
                            grant_type: "refresh_token",
                            refresh_token: token.user.refresh_token,
                        }),
                        method: "POST",
                    })

                    const refreshToken: TokenSet = await response.json()

                    if (!response.ok) throw refreshToken

                    token.user.access_token = refreshToken.access_token


                    token.user.expires_at = Math.floor(Date.now() / 1000) + (refreshToken?.expires_in as number ?? 0)
                    
                    if (refreshToken.refresh_token)
                        token.user.refresh_token = refreshToken.refresh_token
                    else throw refreshToken

                    return token
                } catch (error) {
                    console.error("Error refreshing access token", error)
                    return { ...token, error: "RefreshAccessTokenError" as const }
                }
            }
        },
        session: async ({ session, token }: { session: any; token: any }) => {
            const user = { name: token.name, email: token.email, id: token.sub };
            session.user = user
            session.token = token.user.access_token
            session.refreshToken = token.user.refresh_token
            session.expiresAt = token.user.expires_at
            session.error = token.error
            return session;
        },

    },
    // secret: process.env.NEXTAUTH_SECRET,
    cookies: {
        sessionToken: {
          name: `${COOKIE_PREFIX}next-auth.session-token`,
          options: {
            httpOnly: true,
            sameSite: 'lax',
            path: '/',
            secure: true,
          },
        },
        callbackUrl: {
          name: `${COOKIE_PREFIX}next-auth.callback-url`,
          options: {
            sameSite: 'lax',
            path: '/',
            secure: true,
          },
        },
        csrfToken: {
          name: `${COOKIE_PREFIX}next-auth.csrf-token`,
          options: {
            httpOnly: true,
            sameSite: 'lax',
            path: '/',
            secure: true,
          },
        },
        pkceCodeVerifier: {
          name: `${COOKIE_PREFIX}next-auth.pkce.code_verifier`,
          options: {
            httpOnly: true,
            sameSite: 'lax',
            path: '/',
            secure: true,
            maxAge: COOKIES_LIFE_TIME,
          },
        },
        state: {
          name: `${COOKIE_PREFIX}next-auth.state`,
          options: {
            httpOnly: true,
            sameSite: 'lax',
            path: '/',
            secure: true,
            maxAge: COOKIES_LIFE_TIME,
          },
        },
        nonce: {
          name: `${COOKIE_PREFIX}next-auth.nonce`,
          options: {
            httpOnly: true,
            sameSite: 'lax',
            path: '/',
            secure: true,
          },
        },
      },
};

export async function refreshToken(token: any) {
    try {
        const response = await fetch(process.env.REFRESH_TOKEN_URL!, {
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({
                client_id: process.env.KEYCLOAK_ID!,
                client_secret: process.env.KEYCLOAK_SECRET!,
                grant_type: "refresh_token",
                refresh_token: token.user.refresh_token,
            }),
            method: "POST",
        })

        const refreshToken: TokenSet = await response.json()

        if (!response.ok) throw refreshToken

        token.user.access_token = refreshToken.access_token
        token.user.expires_at = Math.floor(Date.now() / 1000) + (refreshToken?.expires_in as number ?? 0),
            token.user.refresh_token = refreshToken.refresh_token ?? token.user.refresh_token
        return token
    } catch (error) {
        console.error("Error refreshing access token", error)
        return { ...token, error: "RefreshAccessTokenError" as const }
    }
}

export const getServerAuthSession = () => getServerSession(authOptions);

export async function getToken(): Promise<string> {
    const session: Session = await getServerAuthSession();
    if (session?.error === "RefreshAccessTokenError") {
        redirect('/api/auth/signin')
    }
    return session.token
}