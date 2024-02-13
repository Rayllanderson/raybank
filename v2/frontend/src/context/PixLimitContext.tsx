import React, { createContext, useContext, useState, ReactNode, useEffect } from 'react';
import { signIn, useSession } from 'next-auth/react';
import toast from 'react-hot-toast';
import { PixService } from '@/services/PixService';
import { ApiErrorException } from '@/types/Error';

interface PixUpdateLimitContextProps {
  loading: boolean;
  findLimitLoading: boolean;
  findLimit: () => Promise<{ limit: number } | null>;
  updateLimit: (data: number) => Promise<any | null>;
}

const PixUpdateLimitContext = createContext<PixUpdateLimitContextProps | undefined>(undefined);

interface PixUpdateLimitProviderProps {
  children: ReactNode;
}

export const PixUpdateLimitProvider: React.FC<PixUpdateLimitProviderProps> = ({ children }) => {
  const [loading, setLoading] = useState(false);
  const [findLimitLoading, setFindLimitLoading] = useState(false);
  const { data: session } = useSession();

  useEffect(() => {
    if (session?.error === "RefreshAccessTokenError") {
        signIn();
    }
}, [session]);

  const updateLimit = async (newLimit: number): Promise<any | null> => {
    try {
      setLoading(true);
      return await PixService.updateLimit(newLimit, session?.token!);
    } catch (err) {
      handleApiError(err, 'Ocorreu um erro ao ajustar o limite');
      return null;
    } finally {
      setLoading(false);
    }
  };

  const findLimit = async (): Promise<{ limit: number } | null> => {
    try {
      setFindLimitLoading(true);
      return await PixService.findLimit(session?.token!);
    } catch (err) {
      handleApiError(err, 'Ocorreu um erro ao buscar o limite');
      return null;
    } finally {
      setFindLimitLoading(false);
    }
  };

  const handleApiError = (err: any, defaultMessage: string) => {
    if (err instanceof ApiErrorException) {
      if (err.httpStatus === 400 || err.httpStatus === 422) {
        toast.error(err.message);
      }
    } else {
      toast.error(defaultMessage);
    }
  };

  return (
    <PixUpdateLimitContext.Provider value={{ loading, findLimitLoading, findLimit, updateLimit }}>
      {children}
    </PixUpdateLimitContext.Provider>
  );
};

export const usePixUpdateLimit = (): PixUpdateLimitContextProps => {
  const context = useContext(PixUpdateLimitContext);

  if (!context) {
    throw new Error('usePixUpdateLimit deve ser usado dentro de um PixUpdateLimitProvider');
  }

  return context;
};
