import { RegisterPixKeySchemaData } from '@/components/form/RegisterPixKeyForm';
import { PixService } from '@/services/PixService';
import { ApiErrorException } from '@/types/Error';
import { PixKey } from '@/types/Pix';
import { useSession } from 'next-auth/react';
import React, { createContext, useContext, useState, ReactNode } from 'react';
import toast from 'react-hot-toast';

interface PixRegisterKeyContextProps {
  loading: boolean,
  registerKey: (data: RegisterPixKeySchemaData) => Promise<PixKey | any>
}

const PixRegisterKeyContext = createContext<PixRegisterKeyContextProps | undefined>(undefined);

interface PixRegisterKeyProviderProps {
  children: ReactNode;
}

export const PixRegisterKeyProvider: React.FC<PixRegisterKeyProviderProps> = ({ children }) => {
  const [loading, setLoading] = useState(false);
  const { data: session } = useSession();

  const registerKey = async (data: RegisterPixKeySchemaData): Promise<PixKey | null> => {
    try {
      setLoading(true)
      return await PixService.register(data, session?.token!)
    } catch (err) {
      if (err instanceof ApiErrorException) {
        if (err.httpStatus === 401) {
          toast.error('Ocorreu um erro ao registrar chave. Recarregue a página e tente novamente')
        }
        if (err.httpStatus === 400 || err.httpStatus === 422) {
          toast.error(err.message)
        }
      } else
        toast.error('Ocorreu um erro ao registrar chave')
      return Promise.resolve(null)
    } finally {
      setLoading(false)
    }
  }

  return (
    <PixRegisterKeyContext.Provider value={{ registerKey, loading }}>
      {children}
    </PixRegisterKeyContext.Provider>
  );
};

export const usePixRegisterKey = (): PixRegisterKeyContextProps => {
  const context = useContext(PixRegisterKeyContext);

  if (!context) {
    throw new Error('usePixRegisterKey deve ser usado dentro de um PixRegisterKeyProvider');
  }

  return context;
};
