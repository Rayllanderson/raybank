import { ContactCard } from '@/components/ContactCard'
import Link from 'next/link'
import React, { useCallback, useEffect, useState } from 'react'
import { Contact } from '@/types/Contact';
import { findAllContacts } from '@/services/ContactService';
import { useContactContext } from '@/context/ContactContex';

interface Props {
    onClick: (contact: Contact) => void
}

export default function ContactsList({ onClick }: Props) {
    const { contacts, loading, error, findAll } = useContactContext();


    useEffect(() => {
        if(contacts == null)
            findAll();
    }, [])

    if (loading) {
        return <div>Carregando...</div>;
    }

    if (error) {
        return <div>Ocorreu um erro ao buscar seus contatos.</div>;
    }

    return (
        <div className='mt-3 p-1'>
            <h1 className="text-xl font-semibold">Todos os contatos</h1>
            {loading ? (
                <p>Carregando...</p>
            ) : (
                <div className="space-y-4 mt-5 flex flex-col">
                    {contacts?.length === 0 ? (
                        <p>Sem contatos</p>
                    ) : (
                        contacts?.map(contact => (
                            <div key={contact.id} className='w-full' onClick={() => onClick(contact)}>
                                <button
                                    title={contact.name}
                                    key={contact.id}
                                    className="p-0 m-0 bg-transparent border-none cursor-pointer text-current hover:scale-[1.03] transform transition-transform w-full"
                                >
                                    <ContactCard contact={contact} key={contact.id} />
                                </button>
                            </div>
                        ))
                    )}
                </div>
            )}
        </div>
    );
}