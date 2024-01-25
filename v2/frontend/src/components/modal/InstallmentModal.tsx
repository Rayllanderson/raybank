'use client';
import React from 'react';
import { Modal, ModalBody, ModalFooter, ModalHeader } from 'flowbite-react';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import { Installment, InstallmentPlan, getOrdenedInstallments } from '@/types/InstallmentPlan';
import { formartToMonthYear } from '@/utils/DateFormatter';
import Separator from '../Separator';

export function InstallmentModal({ installmentPlan: installmentPlan, show, setOpenModal }: { installmentPlan: InstallmentPlan, show: boolean; setOpenModal: (v: boolean) => void; }) {

    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size='md'>
            <ModalHeader>

                <p>Parcelamento de compra</p>
                <div className='mt-2'>
                    <p className='font-light text-base'>A compra foi parcelada em {installmentPlan.installmentCount}x de {MoneyFormatter.format(installmentPlan.installmentValue)}</p>
                    <p className='text-base'>Valor total {MoneyFormatter.format(installmentPlan.total)}</p>
                </div>
            </ModalHeader>
            <ModalBody className='flex flex-col justify-start space-y-2'>
                <div className='w-full'>
                    <div>
                        {getOrdenedInstallments(installmentPlan.installments).map((installment, index) => {
                            return (
                                <>
                                    <div className={`flex justify-between p-1 ${getTextProperty(installment)}`}>
                                        <div className='flex space-x-1 items-center'>
                                            <p className='font-semibold'>{++index}ª</p>
                                            <p >{formartToMonthYear(installment.dueDate)}</p>
                                        </div>
                                        <div className='flex items-center space-x-2'>
                                            <p>{MoneyFormatter.format(installment.value)}</p>
                                        </div>
                                    </div>
                                    <Separator />
                                </>
                            )
                        })}
                    </div>
                </div>

            </ModalBody>
        </Modal>
    );
}

function getTextProperty(installment: Installment) {
    if (installment.status === 'OPEN' || installment.status === 'OVERDUE') {
        return 'text-red-500'
    }
    if (installment.status === 'PAID' || installment.status === 'REFUNDED') {
        return 'text-green-500'
    }
    if (installment.status === 'CANCELED') {
        return 'line-through'
    }
}