import { DepositBoletoResponse, generateBoleto as generateBoletoService } from '@/services/BoletoService';
import { handlerApiError } from '@/services/HandlerApiError';
import { GenerateBoletoRequest } from '@/types/Boleto';
import { useSession } from 'next-auth/react';
import React, { createContext, useContext, useState, ReactNode } from 'react';

export type BoletoDepositData = {
  amount: number;
  beneficiaryId: string | null;
  beneficiaryType: string | null;
  beneficiaryName: string | null;
  barCode: string | null
  success: boolean
};

type BoletoDepositContextType = {
  boletoDepositData: BoletoDepositData;
  loading: boolean;
  setAmount: (v: number) => void;
  setBeneficiaryId: (value: string) => void;
  setBeneficiaryName: (value: string) => void;
  setBeneficiaryType: (value: string) => void;
  resetDeposit: () => void;
  generateBoleto: (id: string, type: 'account' | 'invoice') => Promise<DepositBoletoResponse | null>;
};

const BoletoDepositContext = createContext<BoletoDepositContextType | undefined>(undefined);

type BoletoDepositProviderProps = {
  children: ReactNode;
};

export const BoletoDepositProvider: React.FC<BoletoDepositProviderProps> = ({ children }) => {

  const [loading, setLoading] = useState(false);
  const { data: session } = useSession();

  const emptyBoletoData = {
    amount: 0,
    beneficiaryId: null,
    beneficiaryType: null,
    beneficiaryName: null,
    barCode: null,
    success:false
  }

  const [depositData, setDepositData] = useState<BoletoDepositData>(emptyBoletoData);

  const resetDeposit = () => {
    setDepositData(emptyBoletoData)
  }

  async function generateBoleto(id: string, type: 'account' | 'invoice'): Promise<DepositBoletoResponse | null> {
    try {
      setLoading(true)
      const response = await generateBoletoService({
        account_holder_id: session?.user?.id,
        value: depositData.amount,
        beneficiary: {
          id: id,
          type: type
        },
      } as GenerateBoletoRequest, session?.token!)
      depositData.barCode = response.barCode
      depositData.success = true
      return response
    } catch (err) {
      handlerApiError(err, 'Não foi gerar boleto, ocorreu um erro no servidor')
      return Promise.resolve(null)
    } finally {
      setLoading(false)
    }
  }

  const setAmount = (value: number) => setDepositData((prevData) => ({ ...prevData, amount: value }));
  const setBeneficiaryId = (value: string) => setDepositData((prevData) => ({ ...prevData, beneficiaryId: value }));
  const setBeneficiaryType = (value: string) => setDepositData((prevData) => ({ ...prevData, beneficiaryType: value }));
  const setBeneficiaryName = (value: string) => setDepositData((prevData) => ({ ...prevData, beneficiaryName: value }));

  return (
    <BoletoDepositContext.Provider value={{ resetDeposit, generateBoleto, loading, boletoDepositData: depositData, setAmount, setBeneficiaryId, setBeneficiaryType, setBeneficiaryName }}>
      {children}
    </BoletoDepositContext.Provider>
  );
};

export const useBoletoDepositContext = () => {
  const context = useContext(BoletoDepositContext);
  if (!context) {
    throw new Error('useBoletoDepositContext deve ser usado dentro de um BoletoDepositProvider');
  }
  return context;
};
