
import { Card as FlowbiteCard } from 'flowbite-react';
import React from 'react'

const theme = {
    "root": {
        "children": "flex h-full flex-col justify-center gap-4 p-5 sm:p-6 md:p-6 lg:pd-6"
    }
}

interface CardProps {
    children: any;
}


export const Card: React.FC<CardProps> = ({ children }) => {
    return (
        <FlowbiteCard className="sm:p-2 md:p-3 lg:p-3" theme={theme}>
            {children}
        </FlowbiteCard>
    )
}
