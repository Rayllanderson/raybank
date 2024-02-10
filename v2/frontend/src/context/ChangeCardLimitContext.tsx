import { ApiErrorException } from '@/types/Error';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import React, { createContext, useContext, useState, ReactNode } from 'react';
import toast from 'react-hot-toast';
import { changeLimit as changeCardLimit } from '@/app/cards/changeLimit';
import { ChangeCardLimitFormData } from '@/types/Card';

interface ChangeCardContextType {
    loading: boolean;
    error: any;
    changeLimit: (data: ChangeCardLimitFormData, token: string) => void;
}

const ChangeCardLimitContext = createContext<ChangeCardContextType | undefined>(undefined);

export function useChangeCardLimitContext() {
    const context = useContext(ChangeCardLimitContext);
    if (!context) {
        throw new Error('useAdjustLimitContext deve ser usado dentro de um AdjustLimitProvider');
    }
    return context;
}

interface ChangeCardLimitProviderProps {
    children: ReactNode;
}

export function ChangeCardLimitProvider({ children }: ChangeCardLimitProviderProps) {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<any>(null);

    async function changeLimit(data: ChangeCardLimitFormData, token: string) {
        try {
            setLoading(true);
            await changeCardLimit(data, token)
            setLoading(false);
            toast.success(`Limite ajustado para ${MoneyFormatter.format(data.limit)}`)
        } catch (error) {
            if (error instanceof ApiErrorException) {
                console.log('Erro da API:', error.message);
                toast.error(error.message);
            } else {
                console.log('Erro genérico:', error);
                toast.error('Ocorreu um erro desconhecido. Tente recarregar a página');
            }
            setError(error);
            setLoading(false);
        }
    }

    const value: ChangeCardContextType = {
        changeLimit,
        loading,
        error
    };

    return (
        <ChangeCardLimitContext.Provider value={value}>
            {children}
        </ChangeCardLimitContext.Provider>
    );
}
