// import KeycloakProvider from 'next-auth/providers/keycloak';

// export const authOptions = {
//   providers: [
//     KeycloakProvider({
//       clientId: process.env.KEYCLOAK_CLIENT_ID!,
//       issuer: process.env.KEYCLOAK_ISSUER_URL!,
//       clientSecret: process.env.KEYCLOAK_CLIENT_SECRET!,
//     }),
//   ],

//   callbacks: {
//     jwt: async ({ token, user }: { token: any; user: any }) => {
//       user && (token.user = user);
//       return token;
//     },
//     session: async ({ session, token }: { session: any; token: any }) => {
//       session.user = token;
//       return session;
//     },
//   },
// };