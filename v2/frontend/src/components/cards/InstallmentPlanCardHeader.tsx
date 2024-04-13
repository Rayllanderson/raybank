'use client';
import React from 'react';
import { FaArrowLeft } from 'react-icons/fa6';
import { HiOutlineShoppingBag } from 'react-icons/hi';
import PreviousPageButton from '@/components/PreviousPageButton';

export function InstallmentCardHeader() {
    return <header className="flex items-center justify-between">
        <PreviousPageButton />
        <div className="flex justify-center">
            <div className="p-4 bg-gray-200 dark:bg-gray-500 rounded-full">
                <HiOutlineShoppingBag className="w-8 h-8" />
            </div>
        </div>
        <div className="invisible">
            <FaArrowLeft className='w-5 h-5' />
        </div>
    </header>;
}
