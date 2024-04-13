import { findAccountByType } from '@/services/AccountService';
import { findAllContactsUsingToken } from '@/services/ContactService';
import { FindAccountByType } from '@/types/Account';
import { signIn, useSession } from 'next-auth/react';
import React, { createContext, useContext, useEffect, useState } from 'react';

interface FindAccountContextType {
    account: FindAccountByType | null
    loading: boolean;
    error: any;
    findAccount: (type: 'account' | 'contact' | 'pix', value: string) => Promise<FindAccountByType | null>;
}

const FindAccountContext = createContext<FindAccountContextType | undefined>(undefined);

export function FindAccountProvider({ children }: { children: React.ReactNode }) {
    const [account, setAccount] = useState<FindAccountByType | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<any>(null);
    const { data: session } = useSession();

    useEffect(() => {
        if (session?.error === "RefreshAccessTokenError") {
            signIn();
        }
    }, [session]);

    const findAccount = async (type: 'account' | 'contact' | 'pix', value: string): Promise<FindAccountByType | null> => {
        try {
            setLoading(true);
            setError(null)
            const data: FindAccountByType = await findAccountByType(type, value, session?.token!);
            setLoading(false);
            return data
        } catch (error) {
            console.log(error)
            setError(error);
            setLoading(false);
            return null
        }
    };

    return (
        <FindAccountContext.Provider value={{ account, loading, error, findAccount }}>
            {children}
        </FindAccountContext.Provider>
    );
}

export function useFindAccountContext() {
    const context = useContext(FindAccountContext);
    if (!context) {
        throw new Error('useFindAccountContext must be used within a ContactProvider');
    }
    return context;
}
