import React, { createContext, useContext, useState, ReactNode } from 'react';

interface PixPaymentContextProps {
  qrCode: string;
  amount: number;
  beneficiaryName: string;
  description: string | null
  setBeneficiaryName: React.Dispatch<React.SetStateAction<string>>
  setQrCode: React.Dispatch<React.SetStateAction<string>>;
  setAmount: React.Dispatch<React.SetStateAction<number>>;
  setDescription: React.Dispatch<React.SetStateAction<string | null>>
}

const PixPaymentContext = createContext<PixPaymentContextProps | undefined>(undefined);

interface PixPaymentProviderProps {
  children: ReactNode;
}

export const PixPaymentProvider: React.FC<PixPaymentProviderProps> = ({ children }) => {
  const [qrCode, setQrCode] = useState<string>('');
  const [beneficiaryName, setBeneficiaryName] = useState<string>('');
  const [amount, setAmount] = useState(0)
  const [description, setDescription] = useState<string | null>(null)

  return (
    <PixPaymentContext.Provider value={{ qrCode, beneficiaryName, amount, description, setDescription, setQrCode, setBeneficiaryName, setAmount }}>
      {children}
    </PixPaymentContext.Provider>
  );
};

export const usePixPayment = (): PixPaymentContextProps => {
  const context = useContext(PixPaymentContext);

  if (!context) {
    throw new Error('usePixPayment deve ser usado dentro de um PixPaymentProvider');
  }

  return context;
};
