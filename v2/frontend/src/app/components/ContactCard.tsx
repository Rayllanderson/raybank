import React from 'react';
import Separator from './Separator';
import { Contact } from '../types/Contact';
import FirstLetterIcon from './FirstLetterIcon';


interface Props {
    contact: Contact
}

export function ContactCard({ contact }: Props) {
    return <div className="component space-y-2">
        <header>
            <div className='header flex justify-between items-center '>
                <div className='flex space-x-2 items-center text-lg'>
                    <div className='rounded-full bg-gray-200 dark:bg-gray-800 p-1 w-7 h-7 flex items-center justify-center'>
                        <FirstLetterIcon letter={contact.name[0]} className='w-3 h-3 ' />
                    </div>
                    <div className='title '>
                        {contact.name}
                    </div>
                </div>
            </div>
        </header>
        <hr className='mt-1 border-separate border-gray-200 dark:border-gray-900' />
    </div>;
}
