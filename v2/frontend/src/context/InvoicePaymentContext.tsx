// InvoicePaymentContext.tsx
import { handlerApiError } from '@/services/HandlerApiError';
import { payInvoice } from '@/services/InvoiceService';
import { Invoice } from '@/types/Invoice';
import { useSession } from 'next-auth/react';
import React, { createContext, useContext, useState, ReactNode } from 'react';

interface InvoicePaymentContextProps {
  amount: number;
  invoice: Invoice | null;
  paymentMethod: 'account' | 'boleto';
  setAmount: React.Dispatch<React.SetStateAction<number>>;
  setInvoice: (n: Invoice) => void;
  setPaymentMethod: React.Dispatch<React.SetStateAction<'account' | 'boleto'>>;
  loading: boolean;
  payCurrent: () => Promise<any | null>
}

const InvoicePaymentContext = createContext<InvoicePaymentContextProps | undefined>(undefined);

interface InvoicePaymentProviderProps {
  children: ReactNode;
}

export const InvoicePaymentProvider: React.FC<InvoicePaymentProviderProps> = ({ children }) => {
  const [amount, setAmount] = useState<number>(0);
  const [invoice, setInvoice] = useState<Invoice | null>(null);
  const [paymentMethod, setPaymentMethod] = useState<'account' | 'boleto'>('account');
  const { data: session } = useSession();
  const [loading, setLoading] = useState(false);

  const payCurrent = async () => {
    try {
      setLoading(true);
      return await payInvoice(amount, session?.token!)
    } catch (err) {
      handlerApiError(err, 'Ocorreu um erro ao pagar a fatura');
      return null;
    } finally {
      setLoading(false);
    }
  }

  return (
    <InvoicePaymentContext.Provider value={{ amount, invoice, setInvoice, paymentMethod, setAmount, setPaymentMethod, payCurrent, loading }}>
      {children}
    </InvoicePaymentContext.Provider>
  );
};

export const useInvoicePayment = (): InvoicePaymentContextProps => {
  const context = useContext(InvoicePaymentContext);

  if (!context) {
    throw new Error('useInvoicePayment deve ser usado dentro de um InvoicePaymentProvider');
  }

  return context;
};
