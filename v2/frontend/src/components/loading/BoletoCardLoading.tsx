import React from 'react';
import { FaCalendarDays } from 'react-icons/fa6';
import { formatDate } from '../../utils/DateFormatter';
import { MoneyFormatter } from '../../utils/MoneyFormatter';
import Separator from '../Separator';
import { Boleto } from '../../types/Boleto';
import LoadingDiv from '../LoadingDiv';


export function BoletoCardLoading() {
    return <div className="component">
        <header>
            <div className='flex justify-between items-center'>
                <div className='flex space-x-2 items-center text-lg'>
                    <div className='w-6'>
                        <LoadingDiv className='rounded-full' />
                    </div>
                    <div className='w-28 '>
                        <LoadingDiv className='rounded-md' />
                    </div>
                </div>
                <div className='flex justify-end'>
                    <div className='w-14'>
                        <LoadingDiv className='rounded-md'/>
                    </div>
                </div>
            </div>
        </header>
        <div className='mt-1'>
            <div className='flex justify-between items-center'>
                <div className='flex space-x-2 items-center text-lg'>
                    <div className='ml-8 w-14'>
                        <LoadingDiv className='rounded-md'/>
                    </div>
                </div>
                <div className='flex justify-end'>
                    <div className='w-20'>
                        <LoadingDiv className='rounded-md'/>
                    </div>
                </div>
            </div>
        </div>
        <Separator />
    </div>;
}
