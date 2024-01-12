import React, { createContext, useContext, ReactNode, useState, Dispatch, SetStateAction } from 'react';
import { TransferTransaction } from '../types/transactions/TransferTransaction';

interface TransferTransactionContextData {
    transaction: TransferTransaction;
    setTransaction: Dispatch<SetStateAction<TransferTransaction>>;
    setBeneficiaryAccountNumber: (value: number | null) => void;
    setAmount: (value: number) => number;
    setMessage: (value: string | null) => void;
}

const TransactionContext = createContext<TransferTransactionContextData | undefined>(undefined);

interface TransactionProviderProps {
    children: ReactNode;
}

const TransactionProvider: React.FC<TransactionProviderProps> = ({ children }) => {
    const [transaction, setTransaction] = useState<TransferTransaction>({
        beneficiaryAccountNumber: null,
        amount: 0,
        message: null,
    });

    const setBeneficiaryAccountNumber = (value: number | null) => {
        setTransaction((prevTransaction) => ({
            ...prevTransaction!,
            beneficiaryAccountNumber: value,
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
        setTransaction,
        setBeneficiaryAccountNumber,
        setAmount,
        setMessage,
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