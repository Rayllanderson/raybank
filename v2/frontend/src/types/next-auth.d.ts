import NextAuth, { DefaultSession, DefaultJWT } from "next-auth";
import { JWT } from "next-auth/jwt";
import { User } from "./User";

declare module "next-auth" {

  interface Session {
    error?: "RefreshAccessTokenError",
    user: User & DefaultSession["user"];
    token: string;
    expiresAt: number;
    refreshToken: string;
  }
}

declare module "next-auth/jwt" {
  interface JWT {
    access_token: string
    expires_at: number
    refresh_token: string
    error?: "RefreshAccessTokenError"
    user: {
      name: string;
    };
  } 
}
