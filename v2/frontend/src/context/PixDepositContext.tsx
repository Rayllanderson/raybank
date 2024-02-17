import { handlerApiError } from '@/services/HandlerApiError';
import { PixService } from '@/services/PixService';
import { useSession } from 'next-auth/react';
import React, { createContext, useContext, useState, ReactNode } from 'react';

type PixDepositData = {
    amount: number;
    message: string | null;
    creditKey: string | null;
};

type PixDepositContextType = {
    pixDepositData: PixDepositData;
    setAmount: (v: number) => void;
    setMessage: (value: string) => void;
    setCreditKey: (value: string) => void;
    loading: boolean;
    generateQrCode: () => Promise<any | null>
    qrCode: string|null
};

const PixDepositContext = createContext<PixDepositContextType | undefined>(undefined);

type PixDepositProviderProps = {
    children: ReactNode;
};

export const PixDepositProvider: React.FC<PixDepositProviderProps> = ({ children }) => {
    const [depositData, setDepositData] = useState<PixDepositData>({
        amount: 0,
        message: null,
        creditKey: null,
    });

    const setAmount = (value: number) => setDepositData((prevData) => ({ ...prevData, amount: value }));
    const setMessage = (value: string) => setDepositData((prevData) => ({ ...prevData, message: value }));
    const setCreditKey = (value: string) => setDepositData((prevData) => ({ ...prevData, creditKey: value }));
    const [loading, setLoading] = useState(false);
    const [qrCode, setQrCode] = useState<string|null>(null);
    const { data: session } = useSession();
    

    async function generateQrCode() {
        try {
            setLoading(true);
            const response = await PixService.generateQrCode(depositData.amount, depositData.creditKey!, depositData.message, session?.token!);
            setQrCode(response.code)
            return response
        } catch (err) {
            handlerApiError(err, 'Ocorreu um erro ao gerar qr code');
            return null;
        } finally {
            setLoading(false);
        }
    }

    return (
        <PixDepositContext.Provider value={{generateQrCode,loading, qrCode, pixDepositData: depositData, setAmount, setMessage, setCreditKey }}>
            {children}
        </PixDepositContext.Provider>
    );
};

export const usePixDepositContext = () => {
    const context = useContext(PixDepositContext);
    if (!context) {
        throw new Error('useDepositContext deve ser usado dentro de um DepositProvider');
    }
    return context;
};
