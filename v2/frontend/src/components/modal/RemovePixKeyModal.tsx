import React, { useState } from 'react';
import { Button, Modal, ModalHeader } from 'flowbite-react';
import { PixKey } from '@/types/Pix';
import { FaCircleExclamation } from 'react-icons/fa6';
import { useSession } from 'next-auth/react';
import { PixService } from '@/services/PixService';
import { ApiErrorException } from '@/types/Error';
import toast from 'react-hot-toast';
import PrimaryButton from '../Buttons/PrimaryButton';
import { useRouter } from 'next/navigation';

export function RemovePixKeyModal({ pixKey, show, setOpenModal }: { pixKey: PixKey, show: boolean; setOpenModal: (v: boolean) => void; }) {
    const [loading, setLoading] = useState(false);
    const { data: session } = useSession();
    const router = useRouter()

    const deleteKey = async (): Promise<any | null> => {
        try {
            setLoading(true)
            await PixService.deleteByKey(pixKey.key, session?.token!)
            return Promise.resolve(true)
        } catch (err) {
            if (err instanceof ApiErrorException) {
                if (err.httpStatus === 400 || err.httpStatus === 422) {
                    toast.error(err.message)
                }
            } else
                toast.error('Ocorreu um erro ao excluir chave')
            return Promise.resolve(null)
        } finally {
            setLoading(false)
        }
    }


    const handleClick = async () => {
        const response = await deleteKey()
        if (response) {
            toast.success(`Chave ${pixKey?.key} excluída com sucesso`)
            setOpenModal(false)
            router.refresh()
        }
    };

    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size='md' popup>
            <ModalHeader />
            <Modal.Body>
                <div className="text-center">
                    <FaCircleExclamation className="mx-auto mb-4 h-14 w-14 text-gray-400 dark:text-gray-200" />
                    <h3 className="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">
                        Tem certeza que deseja excluir a chave Pix <strong>{pixKey.key}</strong>?
                    </h3>
                    <div className="flex justify-center gap-4">
                        <div>
                            <PrimaryButton loading={loading} disabled={loading} onClick={handleClick}>
                                Sim
                            </PrimaryButton>
                        </div>
                        <Button color="light" onClick={() => setOpenModal(false)}>
                            Não
                        </Button>
                    </div>
                </div>
            </Modal.Body>
        </Modal>
    );
}
