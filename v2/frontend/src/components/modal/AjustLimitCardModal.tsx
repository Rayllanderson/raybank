import React, { useState } from 'react';
import { Modal, ModalBody, ModalHeader } from 'flowbite-react';
import FormSliderLimit from '../FormSliderLimit';
import { CardDetails } from '@/types/Card';

export function AjustLimitCardModal({ card, show, setOpenModal }: { show: boolean; setOpenModal: (v: boolean) => void; card: CardDetails}) {
    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size='lg'>
            <ModalHeader>Ajuste o Limite do Cartão de Crédito</ModalHeader>
            <ModalBody className='flex justify-center mb-2'>
                <div className='w-full'>
                    <FormSliderLimit card={card}  />
                </div>
            </ModalBody>
        </Modal>
    );
}
