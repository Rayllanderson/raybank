'use client';
import PreviousPageButton from '@/app/components/PreviousPageButton';
import React from 'react';

interface Props {
    title: string,
    subtitle?: string,
}

export default function TextHeaderForm({ title, subtitle }: Props) {
    return (
        <header className="flex flex-col gap-3">
            <div>
                <PreviousPageButton />
            </div>
            <div className="text-start">
                <h1 className="text-lg md:text-xl lg:text-2xl font-semibold">{title}</h1>
                <p className='text-md md:text-lg text-gray-500 dark:text-gray-400'>{subtitle}</p>
            </div>
        </header>

    )
}