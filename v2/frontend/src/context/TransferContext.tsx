import { TransferTransaction } from '@/types/transactions/TransferTransaction';
import React, { createContext, useContext, ReactNode, useState } from 'react';

interface TransferTransactionContextData {
    transaction: TransferTransaction;
    setBeneficiary: (value: string) => void;
    setBeneficiaryName: (value: string) => void;
    setBeneficiaryType: (value: 'account' | 'pix' | 'contact') => void;
    setAmount: (value: number) => number;
    setMessage: (value: string | null) => void;
    resetTransaction: () => void;
}

const TransactionContext = createContext<TransferTransactionContextData | undefined>(undefined);

interface TransactionProviderProps {
    children: ReactNode;
}

const TransactionProvider: React.FC<TransactionProviderProps> = ({ children }) => {
    const [transaction, setTransaction] = useState<TransferTransaction>({
        beneficiary: null,
        amount: 0,
        message: null,
        beneficiaryType: null,
        beneficiaryName: null
    });

    const resetTransaction = () => {
        setTransaction({
            beneficiary: null,
            amount: 0,
            message: null,
            beneficiaryType: null,
            beneficiaryName: null
        })
    }

    const setBeneficiary = (value: string) => {
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
        setBeneficiary,
        setAmount,
        setBeneficiaryType,
        setMessage,
        setBeneficiaryName,
        resetTransaction
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