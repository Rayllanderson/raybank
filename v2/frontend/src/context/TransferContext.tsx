import { TransferSerice } from '@/services/TransferService';
import { ApiErrorException } from '@/types/Error';
import { TransferTransaction } from '@/types/transactions/TransferTransaction';
import { signIn, useSession } from 'next-auth/react';
import React, { createContext, useContext, ReactNode, useState, useEffect } from 'react';
import toast from 'react-hot-toast';

interface TransferTransactionContextData {
    transaction: TransferTransaction;
    setBeneficiary: (value: string | number) => void;
    setBeneficiaryName: (value: string) => void;
    setBeneficiaryType: (value: 'account' | 'pix' | 'contact') => void;
    setAmount: (value: number) => number;
    setMessage: (value: string | null) => void;
    resetTransaction: () => void;
    transfer: () => Promise<TransferTransaction | null>;
    loading: boolean;
    error: any;
}

const TransactionContext = createContext<TransferTransactionContextData | undefined>(undefined);

interface TransactionProviderProps {
    children: ReactNode;
}

const TransactionProvider: React.FC<TransactionProviderProps> = ({ children }) => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<any>(null);
    const [transaction, setTransaction] = useState<TransferTransaction>({
        beneficiary: null,
        amount: 0,
        message: null,
        beneficiaryType: null,
        beneficiaryName: null,
        success: false
    });

    const { data: session } = useSession();
    useEffect(() => {
        if (session?.error === "RefreshAccessTokenError") {
            signIn();
        }
    }, [session]);

    async function transfer(): Promise<TransferTransaction | null>{
        try {
            setLoading(true)
            await TransferSerice.transfer(transaction, session?.token!)
            setLoading(false)
            transaction.success = true
            return transaction
        } catch (err) {
            setError(err);
            if (err instanceof ApiErrorException && err.httpStatus <= 500) {
                toast.error(err.message)
            } else
                toast.error('Ocorreu um erro ao transferir.')
            setLoading(false)
        }
        return null
    }

    const resetTransaction = () => {
        setTransaction({
            beneficiary: null,
            amount: 0,
            message: null,
            beneficiaryType: null,
            beneficiaryName: null,
            success: false
        })
    }

    const setBeneficiary = (value: string | number) => {
        setTransaction((prevTransaction) => ({
            ...prevTransaction!,
            beneficiary: value,
        }));
    };

    const setBeneficiaryType = (value: 'account' | 'pix' | 'contact') => {
        setTransaction((prevTransaction) => ({
            ...prevTransaction!,
            beneficiaryType: value,
        }));
    };

    const setBeneficiaryName = (value: string) => {
        setTransaction((prevTransaction) => ({
            ...prevTransaction!,
            beneficiaryName: value,
        }));
    };


    const setAmount = (value: number): number => {
        setTransaction((prevTransaction) => ({
            ...prevTransaction!,
            amount: value,
        }));
        return value;
    };

    const setMessage = (value: string | null) => {
        setTransaction((prevTransaction) => ({
            ...prevTransaction!,
            message: value,
        }));
    };

    const contextValue: TransferTransactionContextData = {
        transaction,
        transfer,
        setBeneficiary,
        setAmount,
        setBeneficiaryType,
        setMessage,
        setBeneficiaryName,
        resetTransaction,
        loading,
        error
    };

    return <TransactionContext.Provider value={contextValue}>{children}</TransactionContext.Provider>;
};

const useTransferTransactionContext = (): TransferTransactionContextData => {
    const context = useContext(TransactionContext);

    if (!context) {
        throw new Error('useTransactionContext deve ser usado dentro de um TransactionProvider');
    }

    return context;
};

export { TransactionProvider, useTransferTransactionContext };