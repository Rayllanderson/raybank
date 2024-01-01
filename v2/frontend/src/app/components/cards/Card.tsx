import { Card as FlowbiteCard } from 'flowbite-react';
import React from 'react'

interface CardProps {
    className?: string
    children: any;
}


export const Card: React.FC<CardProps> = ({ className, children }) => {
    return (
        <FlowbiteCard className={`sm:p-2 md:p-3 lg:p-3 ${className}`}>
            {children}
        </FlowbiteCard>
    )
}
