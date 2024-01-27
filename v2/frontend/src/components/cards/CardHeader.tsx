import Link from 'next/link'
import React from 'react'

interface CardHeaderProps {
    title: string,
    linkObject?: any
    linkHref?: string
}

export default function CardHeader({ title, linkObject, linkHref }: CardHeaderProps) {
    return (
        <div className="flex justify-between items-center mt-3">
            <p className="text-xl font-bold dark:text-white">{title}</p>
            {
                linkHref !== undefined &&
                <Link className='text-lg font-bold text-primary-2 hover:text-primary-1 hover:scale-105 transition-all' href={linkHref!!} > {linkObject} </Link>
            }
        </div>
    )
}
