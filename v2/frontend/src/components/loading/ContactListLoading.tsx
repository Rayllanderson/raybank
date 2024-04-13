import React from 'react'
import { ContactCardLoading } from '@/components/loading/ContactCardLoading';

export default function ContactsListLoading() {

    return (
        <div className='mt-3 p-1'>
            <h1 className="text-xl font-semibold">Todos os contatos</h1>
            <div className="space-y-4 mt-5 flex flex-col">
                {(
                    [1,2,3,4,5].map(contact => (
                        <div key={contact} className='w-full'>
                            <div
                                key={contact}
                                className="p-0 m-0 bg-transparent border-none cursor-pointer text-current hover:scale-[1.03] transform transition-transform w-full"
                            >
                                <ContactCardLoading key={contact} />
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}