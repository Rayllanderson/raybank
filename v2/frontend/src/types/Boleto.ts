export interface Boleto {
  barCode: string;
  beneficiary: string;
  value: number;
  status: string;
  createdAt: string;
  expirationDate: string;
}


export interface GenerateBoletoRequest {
  value: number;
  account_holder_id: string;
  beneficiary: Beneficiary;
}

export interface Beneficiary {
  id: string;
  type: 'account' | 'invoice';
}
