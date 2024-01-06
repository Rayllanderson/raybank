'use client';
import React from 'react';
import InputWithIcon from './inputs/InputWithIcon';
import { FaSearch } from 'react-icons/fa';
import { statements } from '../cards/mock';
import StatamentCard from './StatamentCard';

interface Props {
    type: "card" | "account";
}

export function Statements({type}: Props) {
    return <div className="mt-8 p-1">
        <h1 className="text-2xl font-semibold font-mono">Histórico</h1>

        <div className='mt-2'>
            <InputWithIcon icon={FaSearch} type="text" placeholder='Buscar' />
        </div>

        <div className='space-y-2 mt-5'>
            {statements.map(statement => {
                return (
                    <StatamentCard statement={statement} key={statement.id} />
                );
            })}
        </div>
    </div>;
}
