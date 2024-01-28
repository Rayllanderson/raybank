import NextAuth, { DefaultSession, DefaultJWT } from "next-auth";
import { JWT } from "next-auth/jwt";

declare module "next-auth" {

  interface Session extends DefaultSession {
    user: {
      name: string;
      account: Account;
    };
  }
}

declare module "next-auth/jwt" {
  interface JWT {
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