import React from 'react';
import { FaCalendarDays } from 'react-icons/fa6';
import { formatDate } from '../utils/DateFormatter';
import { MoneyFormatter } from '../utils/MoneyFormatter';
import Separator from './Separator';
import { Boleto } from '../types/Boleto';

interface Props {
    boleto: Boleto
}

export function BoletoCard({boleto}: Props) {
    return <div className="component">
        <header>
            <div className='header flex justify-between items-center '>
                <div className='flex space-x-2 items-center text-lg'>
                    <FaCalendarDays className='w-5 h-5 text-primary-2' />
                    <div className='title font-semibold '>
                        {boleto.beneficiary}
                    </div>
                </div>
                <div className='flex justify-end'>
                    <div className='data'>
                        {formatDate(boleto.expirationDate)}
                    </div>
                </div>
            </div>
        </header>
        <div>
            <div className='flex justify-between items-center'>
                <div className='flex space-x-2 items-center text-lg'>
                    <div className='ml-8'>
                        {MoneyFormatter.format(boleto.value)}
                    </div>
                </div>
                <div className='flex justify-end'>
                    <div className='data'>
                        {boleto.status}
                    </div>
                </div>
            </div>
        </div>
        <Separator/>
    </div>;
}
