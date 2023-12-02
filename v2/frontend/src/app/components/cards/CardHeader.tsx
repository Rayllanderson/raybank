import Link from 'next/link'
import React from 'react'

interface CardHeaderProps {
    title: string,
    linkObject: any
    linkHref: string
}

export default function CardHeader({ title, linkObject, linkHref }: CardHeaderProps) {
    return (
        <div className="flex justify-between items-center mt-3">
            <p className="text-lg font-mono font-semibold dark:text-white">{title}</p>
            <Link className='text-lg font-mono font-bold text-primary-2 hover:text-primary-1 hover:scale-105 transition-all' href={linkHref} > {linkObject} </Link>
        </div>
    )
}
