import { userService } from '@/services/UserService';
import { getServerSession } from 'next-auth';
import CredentialsProvider from 'next-auth/providers/credentials';

export const authOptions = {
    providers: [
        CredentialsProvider({
            name: 'Credentials',
            credentials: {
                username: { label: 'Username', type: 'text' },
                password: { label: 'Password', type: 'password' },
            },

            async authorize(credentials, req) {
                const { username, password } = credentials as {
                    username: string
                    password: string
                };

                const user = userService.authenticate(username, password);

                if (user) {
                    return user
                } else {
                    return null
                }
            },
        }),
    ],

    callbacks: {
        jwt: async ({ token, user }: { token: any; user: any }) => {
            if (user) {
                token.token = user.token
                token.account = user.account
            }
            return token;
        },
        session: async ({ session, token }: { session: any, token: any, user: any }) => {
            session.token = token.token
            session.account = token.account
            return session;
        },
    },
    pages: {
        signIn: '/auth/login',
    },
};

export const getServerAuthSession = () => getServerSession(authOptions);