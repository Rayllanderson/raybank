import React, { createContext, useContext, useState, ReactNode } from 'react';

type BoletoDepositData = {
  amount: number;
  beneficiaryId: string | null;
  beneficiaryType: string | null;
  beneficiaryName: string | null;
};

type BoletoDepositContextType = {
  boletoDepositData: BoletoDepositData;
  setAmount: (v: number) => void;
  setBeneficiaryId: (value: string) => void;
  setBeneficiaryName: (value: string) => void;
  setBeneficiaryType: (value: string) => void;
};

const BoletoDepositContext = createContext<BoletoDepositContextType | undefined>(undefined);

type BoletoDepositProviderProps = {
  children: ReactNode;
};

export const BoletoDepositProvider: React.FC<BoletoDepositProviderProps> = ({ children }) => {
  const [depositData, setDepositData] = useState<BoletoDepositData>({
    amount: 0,
    beneficiaryId: null,
    beneficiaryType: null,
    beneficiaryName: null
  });

  const setAmount = (value: number) => setDepositData((prevData) => ({ ...prevData, amount: value }));
  const setBeneficiaryId = (value: string) => setDepositData((prevData) => ({ ...prevData, beneficiaryId: value }));
  const setBeneficiaryType = (value: string) => setDepositData((prevData) => ({ ...prevData, beneficiaryType: value }));
  const setBeneficiaryName = (value: string) => setDepositData((prevData) => ({ ...prevData, beneficiaryName: value }));

  return (
    <BoletoDepositContext.Provider value={{ boletoDepositData: depositData, setAmount, setBeneficiaryId, setBeneficiaryType, setBeneficiaryName }}>
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
