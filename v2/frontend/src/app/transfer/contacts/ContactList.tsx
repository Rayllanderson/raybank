import { ContactCard } from '@/components/ContactCard'
import Link from 'next/link'
import React, { useCallback, useEffect, useState } from 'react'
import { Contact } from '@/types/Contact';
import { findAllContacts } from '@/services/ContactService';
import { useContactContext } from '@/context/ContactContex';
import { ContactCardLoading } from '@/components/loading/ContactCardLoading';

interface Props {
    onClick: (contact: Contact) => void
    disabled:boolean
}

export default function ContactsList({ onClick,disabled }: Props) {
    const { contacts, loading, error, findAll } = useContactContext();


    useEffect(() => {
        if (contacts == null)
            findAll();
    }, [])

    if (error) {
        return <div>Ocorreu um erro ao buscar seus contatos.</div>;
    }

    return (
        <div className='mt-3 p-1'>
            <h1 className="text-xl font-semibold">Todos os contatos</h1>
            {loading ? (
                <div className="space-y-4 mt-5 flex flex-col">
                    <>
                        {[1, 2, 3, 4, 5].map(i => <ContactCardLoading key={i} />)}
                    </>
                </div>
            ) : (
                <div className="space-y-4 mt-5 flex flex-col">
                    {contacts?.length === 0 ? (
                        <p>Sem contatos</p>
                    ) : (
                        contacts?.map(contact => (
                            <div key={contact.id} className='w-full' onClick={() => onClick(contact)}>
                                <button
                                disabled={disabled}

                                    title={contact.name}
                                    key={contact.id}
                                    className={`${disabled && 'cursor-wait'} p-0 m-0 bg-transparent border-none cursor-pointer text-current hover:scale-[1.03] transform transition-transform w-full`}
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