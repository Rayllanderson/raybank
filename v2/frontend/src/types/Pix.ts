export enum PixType {
    PHONE,
    EMAIL,
    RANDOM,
    CPF,
}

export interface PixKey {
    key: string;
    type: string;
    createdAt: string;
}
