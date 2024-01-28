export interface AccountResponse {
    user: User;
    account: Account;
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

export interface User {
    name: string;
    type: string;
}