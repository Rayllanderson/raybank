import { User } from "./User";

export default interface Session {
    user: User;
    token: string;
    expiresAt: number;
    refreshToken: string;
}