// CardPaymentContext.tsx
import React, { createContext, useContext, useState, ReactNode } from 'react';

interface CardPaymentContextProps {
  amount: number;
  paymentMethod: 'account' | 'boleto';
  setAmount: React.Dispatch<React.SetStateAction<number>>;
  setPaymentMethod: React.Dispatch<React.SetStateAction<'account' | 'boleto'>>;
}

const CardPaymentContext = createContext<CardPaymentContextProps | undefined>(undefined);

interface CardPaymentProviderProps {
  children: ReactNode;
}

export const CardPaymentProvider: React.FC<CardPaymentProviderProps> = ({ children }) => {
  const [amount, setAmount] = useState<number>(0);
  const [paymentMethod, setPaymentMethod] = useState<'account' | 'boleto'>('account');

  return (
    <CardPaymentContext.Provider value={{ amount, paymentMethod, setAmount, setPaymentMethod }}>
      {children}
    </CardPaymentContext.Provider>
  );
};

export const useCardPayment = (): CardPaymentContextProps => {
  const context = useContext(CardPaymentContext);

  if (!context) {
    throw new Error('useCardPayment deve ser usado dentro de um CardPaymentProvider');
  }

  return context;
};
