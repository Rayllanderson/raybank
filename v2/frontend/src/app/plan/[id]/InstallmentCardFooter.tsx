'use client'
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import React, { useState } from 'react';
import { FaAngleDown } from 'react-icons/fa6';
import { InstallmentPlan } from '@/types/InstallmentPlan';
import { InstallmentModal } from '@/components/modal/InstallmentModal';

export function InstallmentCardFooter({ installment }: { installment: InstallmentPlan }) {
    const [showModal, setShowModal] = useState(false);
    return (
        <>
            <InstallmentModal installmentPlan={installment} show={showModal} setOpenModal={setShowModal}/>


            <div className='flex flex-col justify-center items-center mt-3'>
                <button className='rounded-md max-w-[12rem] hover:scale-[1.03] transform transition-transform' title='parcelas'
                    onClick={() => setShowModal(true)}
                >
                    <div className='bg-gray-200 dark:bg-gray-500 p-2 text-center rounded-lg flex items-center space-x-2'>
                        <p>{installment.installmentCount}x de {MoneyFormatter.format(installment.installmentValue)} </p>
                        <FaAngleDown className='h-4 w-4' />
                    </div>
                </button>
            </div>
        </>
    );
}
