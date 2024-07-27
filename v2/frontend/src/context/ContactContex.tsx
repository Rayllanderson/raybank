import { findAllContactsUsingToken } from '@/services/ContactService';
import { Contact } from '@/types/Contact';
import { signIn, useSession } from 'next-auth/react';
import React, { createContext, useContext, useEffect, useState } from 'react';

interface ContactContextType {
    contacts: Contact[] | null;
    loading: boolean;
    error: any;
    findAll: () => void;
}

const ContactContext = createContext<ContactContextType | undefined>(undefined);

export function ContactProvider({ children }: { children: React.ReactNode }) {
    const [contacts, setContacts] = useState<Contact[] | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<any>(null);
    const { data: session } = useSession();

    useEffect(() => {
        if (session?.error === "RefreshAccessTokenError") {
            signIn();
        }
    }, [session]);

    const findAll = async () => {
        try {
            setLoading(true);
            const data: Contact[] = await findAllContactsUsingToken(session?.token!);
            setContacts(data);
        } catch (error) {
            setError(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <ContactContext.Provider value={{ contacts, loading, error, findAll }}>
            {children}
        </ContactContext.Provider>
    );
}

export function useContactContext() {
    const context = useContext(ContactContext);
    if (!context) {
        throw new Error('useContactContext must be used within a ContactProvider');
    }
    return context;
}
