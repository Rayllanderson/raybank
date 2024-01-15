'use client';
import React from 'react'
import BankAccountCard from '../../components/cards/BankAccountCard'
import InputWithIcon from '../../components/inputs/InputWithIcon'
import { FaSearch } from 'react-icons/fa'
import { statements } from './mock'
import StatamentCard from '../../components/StatamentCard';
import { Statements } from '../../components/Statements';


export default function page() {
    return (
        <div className="cards flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">
            <BankAccountCard withLinkHeader={false} />

            <div className="mt-8 p-1">
                <Statements type='account' />
            </div>
        </div>
    )

}

