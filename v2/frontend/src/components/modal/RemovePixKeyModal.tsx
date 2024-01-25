import React from 'react';
import { Button, Modal, ModalHeader } from 'flowbite-react';
import { PixKey } from '@/types/Pix';
import { FaCircleExclamation } from 'react-icons/fa6';

export function RemovePixKeyModal({ pixKey, show, setOpenModal }: { pixKey: PixKey, show: boolean; setOpenModal: (v: boolean) => void; }) {
    const handleClick = () => {
        console.log('excluiu chave ' + pixKey.key)
    };

    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size='md' popup>
            <ModalHeader />
            <Modal.Body>
                <div className="text-center">
                    <FaCircleExclamation className="mx-auto mb-4 h-14 w-14 text-gray-400 dark:text-gray-200" />
                    <h3 className="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">
                        Tem certeza que deseja excluir a chave PIX <strong>{pixKey.key} </strong>?
                    </h3>
                    <div className="flex justify-center gap-4">
                        <Button color="primary" onClick={handleClick}>
                            Sim
                        </Button>
                        <Button color="light" onClick={() => setOpenModal(false)}>
                            Não
                        </Button>
                    </div>
                </div>
            </Modal.Body>
        </Modal>
    );
}
