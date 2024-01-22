import Link from 'next/link';
import React from 'react';

interface Props {
    text: string,
    linkText: string,
    href: string,
    className?: string
}

export function AuthFooter({ text, linkText, href, className }: Props) {
    return <div className={`flex max-w-md items-center justify-center mt-5 dark:text-white text-gray-800 text-sm md:text-md lg:text-lg ${className}`}>
        <span>{text} &nbsp;</span>

        <Link href={href} className='flex items-center text-primary-2 hover:text-primary-1 transition ease-in duration-100'>
            <span> {linkText} </span>
        </Link>
    </div>;
}
