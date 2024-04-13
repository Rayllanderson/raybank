import { Card as FlowbiteCard } from 'flowbite-react';
import React from 'react'

interface CardProps {
    className?: string
    children: any;
}


export const Card: React.FC<CardProps> = ({ className, children }) => {
    return (
        <FlowbiteCard className={`sm:p-2 md:p-3 lg:p-3 ${className} flex rounded-lg border border-gray-200 bg-white shadow-md dark:border-black-3 dark:bg-black-2`}>
            {children}
        </FlowbiteCard>
    )
}
