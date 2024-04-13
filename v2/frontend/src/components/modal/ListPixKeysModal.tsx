import React from 'react';
import { Modal, ModalBody, ModalHeader } from 'flowbite-react';
import { PixKeyListElement } from '../PixKeyList';
import { PixKey } from '@/types/Pix';

export function ListPixKeysModal({ show, setOpenModal, pixKeys }: { show: boolean; setOpenModal: (v: boolean) => void; pixKeys: PixKey[] }) {
    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size='lg'>
            <ModalHeader>Suas Chaves Pix</ModalHeader>
            <ModalBody className='flex flex-col justify-start space-y-2'>
                <div className='w-full'>
                    {
                        pixKeys.length === 0 ? <div>Nenhuma chave registrada</div> : (
                            pixKeys.map((pixKey) => {
                                return (
                                    <PixKeyListElement key={pixKey.key} pixKey={pixKey} />
                                )
                            })
                        )
                    }
                </div>
                <div>
                </div>
            </ModalBody>
        </Modal>
    );
}

