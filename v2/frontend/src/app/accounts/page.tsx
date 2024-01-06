'use client';
import React from 'react'
import BankAccountCard from '../components/cards/BankAccountCard'
import InputWithIcon from '../components/inputs/InputWithIcon'
import { FaSearch } from 'react-icons/fa'
import { statements } from './mock'
import StatamentCard from '../components/StatamentCard';


export default function page() {
    return (
        <div className="cards flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">
            <BankAccountCard withLinkHeader={false} />

            <div className="mt-8 p-1">
                <h1 className="text-2xl font-semibold font-mono">Histórico</h1>

                <div className='mt-2'>
                    <InputWithIcon icon={FaSearch} type="text" placeholder='Buscar' />
                </div>

                <div className='space-y-2 mt-5'>
                    {statements.map(statement => {
                        return (
                            <StatamentCard statement={statement} key={statement.id}/>
                        )
                    })}
                    

                </div>
            </div>
        </div>
    )

}

