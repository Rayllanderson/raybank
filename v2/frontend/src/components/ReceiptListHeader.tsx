import Separator from '@/components/Separator';
import React from 'react';
import { FaRegMoneyBill1 } from 'react-icons/fa6';


export function ReceiptListHeader({ value }: { value: 'Origem' | 'Destino' | string; }) {
    return (
        <>
            <Separator className='mt-0' type='normal' />
            <div className={`mt-2 mb-[-5px] flex items-center space-x-1 ${value === 'Destino' && 'text-green-500'} ${value === 'Origem' && `text-red-500`}`}>
                <div>
                    <FaRegMoneyBill1 className='w-5 h-5' />
                </div>
                <p className='text-lg font-semibold'>{value}</p>
            </div>

        </>);
}
