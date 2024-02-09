// cardContext.tsx
import { CardService } from '@/services/CardService';
import { CreateCardFormData } from '@/types/Card';
import { ApiErrorException } from '@/types/Error';
import { createContext, useContext, useState, ReactNode } from 'react';
import toast from 'react-hot-toast';

interface CardContextType {
    loading: boolean;
    error: any;
    createCard: (data: CreateCardFormData, token: string) => Promise<void>;
}

const CardContext = createContext<CardContextType | undefined>(undefined);

export function useCardContext() {
    const context = useContext(CardContext);
    if (!context) {
        throw new Error('useCardContext deve ser usado dentro de um CardProvider');
    }
    return context;
}

interface CardProviderProps {
    children: ReactNode;
}

export function CardProvider({ children }: CardProviderProps) {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<any>(null);

    async function createCard(data: CreateCardFormData, token: string) {
        try {
            setLoading(true);
            await CardService.createCard(data, token).then(() => {
                setLoading(false);
            });
            
        } catch (error) {
            if (error instanceof ApiErrorException) {
                console.log('Erro da API:', error.message);
                toast.error(error.message);
            } else {
                console.log('Erro genérico:', error);
                toast.error('Ocorreu um erro desconhecido.');
            }
            setError(error);
            setLoading(false);
        }
    }

    const value: CardContextType = {
        loading,
        error,
        createCard,
    };

    return (
        <CardContext.Provider value={value}>
            {children}
        </CardContext.Provider>
    );
}
