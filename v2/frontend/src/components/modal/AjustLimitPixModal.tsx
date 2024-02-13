'use client';
import React, { useEffect, useState } from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'flowbite-react';
import PixUpdateLimitForm from '../form/PixUpdateLimitForm';

export function AjustLimitPixModal({ show, setOpenModal }: { show: boolean; setOpenModal: (v: boolean) => void; }) {
    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size='md'>
            <ModalHeader>Limite total</ModalHeader>
            <ModalBody className='flex flex-col justify-start space-y-2'>
                <PixUpdateLimitForm closeModal={() => setOpenModal(false)}/>
            </ModalBody>
        </Modal>
    );
}
