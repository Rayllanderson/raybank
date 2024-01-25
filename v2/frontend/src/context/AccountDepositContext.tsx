import { createContext, useContext, useState, ReactNode } from 'react';

interface AccountDepositContextType {
  amount: number;
  setAmount: (value: number) => void;
}

const AccountDepositContext = createContext<AccountDepositContextType | undefined>(undefined);

export const useAccountDeposit = () => {
  const context = useContext(AccountDepositContext);
  if (!context) {
    throw new Error('useAccountDeposit must be used within a AccountDepositProvider');
  }
  return context;
};

interface AccountDepositProviderProps {
  children: ReactNode;
}

export const AccountDepositProvider = ({ children }: AccountDepositProviderProps) => {
  const [amount, setAmount] = useState<number>(0);

  const value: AccountDepositContextType = {
    amount,
    setAmount,
  };

  return (
    <AccountDepositContext.Provider value={value}>
      {children}
    </AccountDepositContext.Provider>
  );
};
