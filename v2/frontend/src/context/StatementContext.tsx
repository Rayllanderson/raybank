import { getAllCardStatementsWithToken } from '@/services/StatementService';
import { Page } from '@/types/Page';
import { Statement } from '@/types/Statement';
import { signIn, useSession } from 'next-auth/react';
import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';

interface CardStatementContextType {
    pagination: Page<Statement>;
    loading: boolean;
}

const CardStatementContext = createContext<CardStatementContextType | undefined>(undefined);

export function useCardStatement(): CardStatementContextType {
    const context = useContext(CardStatementContext);
    if (!context) {
        throw new Error('useCardStatement must be used within a CardStatementProvider');
    }
    return context;
}

interface CardStatementProviderProps {
    children: ReactNode;
}
export function CardStatementProvider({ children }: CardStatementProviderProps) {
    const [pagination, setPagination] = useState<Page<Statement>>({
        page: 0,
        size: 1,
        total: 0,
        items: []
      });
    const [page, setPage] = useState(0);
    const { data: session } = useSession();
    const [loading, setLoading] = useState(true);
    const [canFetch, setCanFetch] = useState(true);

    useEffect(() => {
        if (session?.error === "RefreshAccessTokenError") {
            signIn();
        }
    }, [session]);

    useEffect(() => {
        async function fetchItems() {
            setLoading(true);
            const data: Page<Statement> =  await getAllCardStatementsWithToken(session?.token!, { page: page, size: 10 }) 
            setPagination(prevPagination => ({
                ...data,
                items: [...prevPagination.items, ...data.items]
            }));
            setCanFetch(data.items.length > 0);
            setLoading(false);
        }

        fetchItems();
    }, [session?.token!, page]);

    function handleScroll() {
        if (window.innerHeight + document.documentElement.scrollTop === document.documentElement.offsetHeight) {
            if (canFetch)
                setPage(prevPage => prevPage + 1);
        }
    }

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, [pagination]);



    const value: CardStatementContextType = {
        pagination,
        loading,
    };

    return (
        <CardStatementContext.Provider value={value}>
            {children}
        </CardStatementContext.Provider>
    );
}
