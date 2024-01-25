'use client'
import { PixKey } from '@/types/Pix'
import React, { useState } from 'react'
import { FaTrash } from 'react-icons/fa6'
import { RemovePixKeyModal } from '../modal/RemovePixKeyModal';

export default function ButtonRemovePixKey({ pixKey }: { pixKey: PixKey }) {
    const [showModal, setShowModal] = useState(false);
    return (
        <>
            <RemovePixKeyModal pixKey={pixKey} show={showModal} setOpenModal={setShowModal}/>

            <button title='Deletar chave pix' onClick={() => setShowModal(true)}>
                <FaTrash className='text-red-600' />
            </button>
        </>
    )
}
