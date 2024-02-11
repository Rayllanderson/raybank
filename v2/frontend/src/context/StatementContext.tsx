import { getAllCardStatementsWithToken } from '@/services/StatementService';
import { Page } from '@/types/Page';
import { Statement } from '@/types/Statement';
import { signIn, useSession } from 'next-auth/react';
import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';

interface StatementContextType {
    pagination: Page<Statement>;
    loading: boolean;
    setType: (e:'account'| 'card') => void
}

const StatementContext = createContext<StatementContextType | undefined>(undefined);

export function useFindStatement(): StatementContextType {
    const context = useContext(StatementContext);
    if (!context) {
        throw new Error('useCardStatement must be used within a CardStatementProvider');
    }
    return context;
}

interface StatementProviderProps {
    children: ReactNode;
}
export function StatementProvider({ children }: StatementProviderProps) {
    const [pagination, setPagination] = useState<Page<Statement>>({
        page: 0,
        size: 1,
        total: 0,
        items: []
      });
    const [page, setPage] = useState(0);
    const [type, setType] = useState<'account'| 'card'>();
    const { data: session } = useSession();
    const [loading, setLoading] = useState(true);
    const [canFetch, setCanFetch] = useState(true);

    useEffect(() => {
        if (session?.error === "RefreshAccessTokenError") {
            signIn();
        }
    }, [session]);

    useEffect(() => {
        setPagination({
            page: 0,
            size: 1,
            total: 0,
            items: []
        });
        setPage(0)
    }, [type]);

    useEffect(() => {
        async function fetchItems() {
            setLoading(true);
            const data: Page<Statement> =  await getAllCardStatementsWithToken(session?.token!, { type: type!, page: page, size: 10 }) 
            console.log(data)
            setPagination(prevPagination => ({
                ...data,
                items: [...prevPagination.items, ...data.items]
            }));
            setCanFetch(data.items.length > 0);
            setLoading(false);
        }

        fetchItems();
    }, [session?.token!, page, type]);

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



    const value: StatementContextType = {
        pagination,
        loading,
        setType
    };

    return (
        <StatementContext.Provider value={value}>
            {children}
        </StatementContext.Provider>
    );
}
