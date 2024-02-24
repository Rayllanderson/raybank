import { BoletoService, findBoletoByBarCodeUsingToken } from '@/services/BoletoService';
import { handlerApiError } from '@/services/HandlerApiError';
import { Boleto, BoletoDetailsResponse } from '@/types/Boleto';
import { useSession } from 'next-auth/react';
import React, { createContext, useContext, useState, ReactNode } from 'react';

interface BoletoPaymentContextProps {
    barCode: number | null;
    beneficiaryName: string;
    amount: number;
    loading: boolean;
    setBarCode: React.Dispatch<React.SetStateAction<number>>;
    setBeneficiaryName: React.Dispatch<React.SetStateAction<string>>;
    setAmount: React.Dispatch<React.SetStateAction<number>>;
    payBoleto:() => Promise<any|null>;
    findBoleto:() => Promise<BoletoDetailsResponse|null>;
    boleto:BoletoDetailsResponse|null
    resetPayment: () => void
}

const BoletoPaymentContext = createContext<BoletoPaymentContextProps | undefined>(undefined);

interface BoletoPaymentProviderProps {
    children: ReactNode;
}

export const BoletoPaymentProvider: React.FC<BoletoPaymentProviderProps> = ({ children }) => {
    const [barCode, setBarCode] = useState<number>(0);
    const [beneficiaryName, setBeneficiaryName] = useState<string>('');
    const [boleto,setBoleto] = useState<BoletoDetailsResponse|null>(null)
    const [amount, setAmount] = useState<number>(0);
    const [loading, setLoading] = useState(false);
    const { data: session } = useSession();

    const findBoleto = async () => {
        try {
            setLoading(true);
            const response = await findBoletoByBarCodeUsingToken(barCode?.toString()!, session?.token!);
            response.success = true
            setBoleto(response)
            return response
        } catch (err) {
            handlerApiError(err, 'Ocorreu um erro ao buscar os dados do boleto');
            return null;
        } finally {
            setLoading(false);
        }
    }

    const payBoleto = async () => {
        try {
            setLoading(true);
            return await BoletoService.payBoleto(barCode?.toString()!, session?.token!);
        } catch (err) {
            handlerApiError(err, 'Ocorreu um erro ao pagar o boleto');
            return null;
        } finally {
            setLoading(false);
        }
    }

    const resetPayment = () => {
        setBoleto(null)
        setBarCode(0)
        setBeneficiaryName('')
    }

    return (
        <BoletoPaymentContext.Provider value={{resetPayment,loading, barCode, beneficiaryName, amount, setBarCode, setBeneficiaryName, setAmount,payBoleto,findBoleto,boleto }}>
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
