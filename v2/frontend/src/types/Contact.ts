export interface Contact {
    id: string;
    name: string;
    account: Account;
}

export interface Account {
    number: string;
    key: string;
}
