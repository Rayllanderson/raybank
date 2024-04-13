export enum PixType {
    PHONE = "PHONE",
    EMAIL = "EMAIL",
    RANDOM = "RANDOM",
    CPF = "CPF",
}

export interface PixKey {
    key: string;
    type: string;
    createdAt: string;
}


export interface Pix {
    id: string;
    amount: number;
    type: string;
    debit: Debit;
    credit: Credit;
    message: string;
    occuredOn: string;
    returns: Return[];
}

export interface Credit {
    name: string;
    key: string;
}

export interface Debit {
    name: string;
}

export interface Return {
    id: string;
    amount: number;
    occuredOn: Date;
    message: string;
}


export interface QrCode {
    id:          string;
    code:        string;
    amount:      number;
    status:      string;
    creditKey:   string;
    creditName:   string;
    expiresIn:   Date;
    description: string;
    success: boolean;
}
