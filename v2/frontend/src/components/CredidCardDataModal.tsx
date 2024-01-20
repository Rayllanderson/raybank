'use client';
import React from 'react';
import { Modal, ModalBody, ModalHeader } from 'flowbite-react';
import CreditCard from '@/components/CreditCard';

export function CredidCardDataModal({ show, setOpenModal }: { show: boolean; setOpenModal: (v: boolean) => void; }) {
    return (
        <Modal show={show} onClose={() => setOpenModal(false)}>
            <ModalHeader>Dados do Cartão de Crédito</ModalHeader>
            <ModalBody>
                <CreditCard number='4642348998677632' cvv={711} expiryDate='2031-07' name='jose rafael' />
            </ModalBody>
        </Modal>
    );
}
