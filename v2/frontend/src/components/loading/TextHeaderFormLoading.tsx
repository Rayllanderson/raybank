import PreviousPageButton from '@/components/PreviousPageButton';
import React from 'react';

interface Props {
    title: any,
    subtitle?: any,
}

export default function TextHeaderFormLoading({ title, subtitle }: Props) {
    return (
        <header className="flex flex-col gap-3">
            <div>
                <PreviousPageButton />
            </div>
            <div className="text-start">
                <div className="text-lg md:text-xl lg:text-2xl font-semibold">{title}</div>
                <div className='text-md md:text-lg text-gray-500 dark:text-gray-400'>{subtitle}</div>
            </div>
        </header>
    )
}