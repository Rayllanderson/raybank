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

    return (
        <PixDepositContext.Provider value={{ pixDepositData: depositData, setAmount, setMessage, setCreditKey }}>
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
