import { userService } from '@/services/UserService';
import { getServerSession } from 'next-auth';
import KeycloakProvider from "next-auth/providers/keycloak";
import CredentialsProvider from "next-auth/providers/credentials";

export const authOptions = {

    providers: [
        KeycloakProvider({
            clientId: process.env.KEYCLOAK_ID!,
            clientSecret: process.env.KEYCLOAK_SECRET!,
            issuer: process.env.KEYCLOAK_ISSUER!,
        })
    ],

    callbacks: {
        async jwt({ token, account }: { token: any; account: any }) {
            account && (token.user = account);
            return token;
        },
        session: async ({ session, token }: { session: any; token: any }) => {
            console.log('session' + JSON.stringify(token, null, 2))
            const user = {name: token.name, email: token.email, id: token.sub};
            session.user = user
            session.token = token.user.access_token
            session.refreshToken = token.user.refresh_token
            return session;
        },
    }
};

export const getServerAuthSession = () => getServerSession(authOptions);