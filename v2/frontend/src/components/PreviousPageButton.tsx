'use client'
import { useRouter } from 'next/navigation';
import React from 'react'
import { FaArrowLeft } from 'react-icons/fa6';

export default function PreviousPageButton() {
    const router = useRouter();
    const goToPreviousRoute = () => {
        router.back();
    };

    return (
        <button onClick={goToPreviousRoute} className="p-0 m-0 bg-transparent border-none cursor-pointer text-current hover:scale-110 transform transition-transform" title='back'>
            <FaArrowLeft className='w-5 h-5 text-primary-2' />
        </button>
    )
}
