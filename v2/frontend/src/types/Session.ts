import { Account, User } from "./User";

export default interface Session {
    user: User;
    token: string;
    refreshToken: string;
}