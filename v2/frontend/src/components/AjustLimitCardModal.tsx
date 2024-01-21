'use client';
import React, { useState } from 'react';
import { Modal, ModalBody, ModalHeader } from 'flowbite-react';
import CreditCard from '@/components/CreditCard';
import FormAjustLimit from './FormAjustLimit';

export function AjustLimitCardModal({ show, setOpenModal }: { show: boolean; setOpenModal: (v: boolean) => void; }) {
    const [limit, setLimit] = useState(7500);

    const handleSliderChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setLimit(Number(event.target.value));
        //mandar para api
        //add toast
    };

    return (
        <Modal show={show} onClose={() => setOpenModal(false)}>
            <ModalHeader>Ajuste o Limite do Cartão de Crédito</ModalHeader>
            <ModalBody className='flex justify-center'>
                <div className='w-full'>
                    <FormAjustLimit limit={limit} handleSliderChange={handleSliderChange} />
                </div>
            </ModalBody>
        </Modal>
    );
}
