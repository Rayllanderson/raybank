'use client';
import React, { useState } from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'flowbite-react';
import { CurrencyInput } from './inputs/InputMoney';
import { MoneyFormatter } from '@/utils/MoneyFormatter';

export function AjustLimitPixModal({ show, setOpenModal }: { show: boolean; setOpenModal: (v: boolean) => void; }) {
    const currentLimit = 5000
    const [limit, setLimit] = useState(currentLimit);
    
    function onInputChange(value: any) {
        const inputNumber = parseFloat(value || '0');
        setLimit(inputNumber)
    }

    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size='md'>
            <ModalHeader>Limite total</ModalHeader>
            <ModalBody className='flex flex-col justify-start space-y-2'>
                <div className='w-full'>
                    <CurrencyInput value={limit} onValueChange={onInputChange} />
                </div>
                <div>
                   <p>Limite atual {MoneyFormatter.format(currentLimit)}</p> 
                </div>
            </ModalBody>
            <ModalFooter className='flex justify-end'>
                <Button color='primary'>Ajustar</Button>
                <Button color='light' onClick={() => setOpenModal(false)}>Cancelar</Button>
            </ModalFooter>
        </Modal>
    );
}
