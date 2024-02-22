import { deposit } from '@/services/AccountService';
import { ApiErrorException } from '@/types/Error';
import { createContext, useContext, useState, ReactNode } from 'react';
import toast from 'react-hot-toast';

interface AccountDepositContextType {
  amount: number;
  loading: boolean;
  doDeposit: (t: string) => any;
  setAmount: (value: number) => void;
  resetDeposit: () => void;
  accountDepositData: AccountDepositData
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

export type AccountDepositData = {
  amount: number;
  success: boolean;
}

const newDepositData = {
  amount: 0,
  success: false
}

export const AccountDepositProvider = ({ children }: AccountDepositProviderProps) => {
  const [amount, setAmount] = useState<number>(0);
  const [accountDepositData, setAccountDepositData] = useState<AccountDepositData>(newDepositData)
  const [loading, setLoading] = useState(false)

  const resetDeposit = () => {
    setAmount(0)
    setAccountDepositData(newDepositData)
  }

  async function doDeposit(token: string) {
    try {
      setLoading(true)
      const response = await deposit(amount, token)
      accountDepositData.success = true
      accountDepositData.amount = amount
      return response
    } catch (err) {
      if (err instanceof ApiErrorException && err.httpStatus <= 500) {
        toast.error(err.message)
      } else
        toast.error('Erro ao realizar depósito')
      return null
    } finally {
      setLoading(false)
    }
  }

  const value: AccountDepositContextType = {
    amount,
    resetDeposit,
    loading,
    accountDepositData,
    doDeposit,
    setAmount,
  };

  return (
    <AccountDepositContext.Provider value={value}>
      {children}
    </AccountDepositContext.Provider>
  );
};
