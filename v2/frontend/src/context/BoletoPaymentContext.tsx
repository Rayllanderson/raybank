// BoletoPaymentContext.tsx
import React, { createContext, useContext, useState, ReactNode } from 'react';

interface BoletoPaymentContextProps {
    barCode: number | null;
    beneficiaryName: string;
    amount: number;
    setBarCode: React.Dispatch<React.SetStateAction<number>>;
    setBeneficiaryName: React.Dispatch<React.SetStateAction<string>>;
    setAmount: React.Dispatch<React.SetStateAction<number>>;
}

const BoletoPaymentContext = createContext<BoletoPaymentContextProps | undefined>(undefined);

interface BoletoPaymentProviderProps {
    children: ReactNode;
}

export const BoletoPaymentProvider: React.FC<BoletoPaymentProviderProps> = ({ children }) => {
    const [barCode, setBarCode] = useState<number>(0);
    const [beneficiaryName, setBeneficiaryName] = useState<string>('');
    const [amount, setAmount] = useState<number>(0);

    return (
        <BoletoPaymentContext.Provider value={{ barCode, beneficiaryName, amount, setBarCode, setBeneficiaryName, setAmount }}>
            {children}
        </BoletoPaymentContext.Provider>
    );
};

export const useBoletoPayment = (): BoletoPaymentContextProps => {
    const context = useContext(BoletoPaymentContext);

    if (!context) {
        throw new Error('useBoletoPayment deve ser usado dentro de um BoletoPaymentProvider');
    }

    return context;
};
