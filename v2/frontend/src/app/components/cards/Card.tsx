import { Card as FlowbiteCard } from 'flowbite-react';
import React from 'react'

interface CardProps {
    children: any;
}


export const Card: React.FC<CardProps> = ({ children }) => {
    return (
        <FlowbiteCard className="sm:p-2 md:p-3 lg:p-3">
            {children}
        </FlowbiteCard>
    )
}
