import React from 'react';
import { Contact } from '../../types/Contact';
import FirstLetterIcon from '../FirstLetterIcon';
import LoadingDiv from '../LoadingDiv';

export function ContactCardLoading() {
    return <div className="component space-y-2">
        <header>
            <div className='header flex justify-between items-center '>
                <div className='flex space-x-2 items-center text-lg'>
                    <div className='rounded-full  p-1 w-7 h-7 flex items-center justify-center'>
                        <LoadingDiv className='rounded-full ' />
                    </div>
                    <div className='title w-40'>
                        <LoadingDiv className='rounded-md '/>
                    </div>
                </div>
            </div>
        </header>
        <hr className='mt-1 border-separate border-gray-200 dark:border-gray-900' />
    </div>;
}
