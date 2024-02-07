import NextAuth, { DefaultSession, DefaultJWT } from "next-auth";
import { JWT } from "next-auth/jwt";

declare module "next-auth" {

  interface Session extends DefaultSession {
    error?: "RefreshAccessTokenError",
    user: User;
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
      account: Account;
    };
  } 
}

export interface Account {
    id:       string;
    number:   number;
    balance:  number;
    status:   string;
    card:     Card;
    
}

export interface Card {
    id:     string;
    status: string;
}