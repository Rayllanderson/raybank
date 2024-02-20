import PreviousPageButton from '@/components/PreviousPageButton';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import React from 'react';
import LoadingDiv from '../LoadingDiv';

export function ConfirmTransactionHeaderLoading({ title }: { title: any }) {
    return <header className="flex flex-col gap-3">
        <div>
            <PreviousPageButton />
        </div>
        <div className="text-start">
            <h1 className="text-lg md:text-xl lg:text-2xl font-semibold">{title}</h1>
            <div className="text-3xl md:text-4xl font-semibold text-primary-2 w-28 ">
                <LoadingDiv className='h-8  rounded-md' />
            </div>
            <div className='flex items-center mt-1'>
                <div className='md:text-lg text-gray-500 dark:text-gray-400'>
                    <div>para: &nbsp;</div>
                </div>
                <div className='w-36'>
                    <LoadingDiv className='rounded-md' />
                </div>
            </div>
        </div>
    </header>
}
