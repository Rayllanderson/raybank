import { handlerApiError } from '@/services/HandlerApiError';
import { PixService } from '@/services/PixService';
import { QrCode } from '@/types/Pix';
import { useSession } from 'next-auth/react';
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
  loading: boolean
  findPix: () => Promise<QrCode | null>
  resetPayment: () => void,
  payPixQrCode: () => any,
  pix: QrCode | null
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
  const [loading, setLoading] = useState(false);
  const [pix, setPix] = useState<QrCode | null>(null)
  const { data: session } = useSession();

  const findPix = async () => {
    try {
      setLoading(true);
      const response = await PixService.findByQrCode(qrCode, session?.token!);
      response.success = true
      setPix(response)
      return response
    } catch (err) {
      handlerApiError(err, 'Ocorreu um erro ao buscar os dados do Pix QrCode');
      return null;
    } finally {
      setLoading(false);
    }
  }

  const payPixQrCode = async () => {
    try {
      setLoading(true);
      return await PixService.payQrCode(pix?.code!, session?.token!);
    } catch (err) {
      handlerApiError(err, 'Ocorreu um erro ao pagar Pix');
      return null;
    } finally {
      setLoading(false);
    }
  }

  const resetPayment = () => {
    setPix(null)
    setQrCode('')
    setBeneficiaryName('')
    setAmount(0)
    setDescription('')
  }

  return (
    <PixPaymentContext.Provider value={{ resetPayment,qrCode, beneficiaryName, amount, description, pix, findPix, payPixQrCode, loading, setDescription, setQrCode, setBeneficiaryName, setAmount }}>
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
