import { Card } from "./Card";
import { User } from "./User";

export interface Account {
    id:       string;
    number:   number;
    balance:  number;
    status:   string;
    card:     Card;
}

export interface AccountResponse {
    user: User;
    account: Account;
}

export interface FindAccountByType {
    number:   number;
    name: string;
}