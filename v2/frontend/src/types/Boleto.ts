export interface Boleto {
    barCode: string;
    beneficiary: string;
    value: number;
    status: string;
    createdAt: string;
    expirationDate: string;
  }