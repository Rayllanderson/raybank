import React from 'react';
import { Modal, ModalBody, ModalHeader } from 'flowbite-react';
import { keys } from '@/app/pix/mock';
import { PixKeyListElement } from '../PixKeyList';


export function ListPixKeysModal({show, setOpenModal }: { show: boolean; setOpenModal: (v: boolean) => void; }) {

    const pixKeys = keys

    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size='lg'>
            <ModalHeader>Suas Chaves Pix</ModalHeader>
            <ModalBody className='flex flex-col justify-start space-y-2'>
                <div className='w-full'>
                    {pixKeys.map((pixKey) => {
                        return (
                            <PixKeyListElement key={pixKey.key} pixKey={pixKey} />
                        )
                    })}
                </div>
                <div>
                </div>
            </ModalBody>
        </Modal>
    );
}

