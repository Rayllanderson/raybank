export interface Boleto {
  barCode: string;
  title: string;
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

export interface BoletoDetailsResponse {
  boleto:             Boleto;
  beneficiary:        BeneficiaryResponse;
  institutionIssuing: InstitutionIssuing;
}

interface BeneficiaryResponse {
  type:    string;
  account?: Account;
  invoice?: {id:string}
}

interface Account {
  id:     string;
  name:   string;
  number: string;
}

interface InstitutionIssuing {
  code: number;
  name: string;
}
