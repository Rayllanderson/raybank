'use client';
import { AjustLimitPixModal } from '@/components/modal/AjustLimitPixModal';
import { MiniCard } from '@/components/cards/MiniCard'
import React, { useState } from 'react'
import { FaKey, FaListUl, FaMoneyBill, FaMoneyBillTransfer, FaQrcode, FaSliders } from 'react-icons/fa6';
import { ListPixKeysModal } from '@/components/modal/ListPixKeysModal';
import { RegisterPixKeyModal } from '@/components/modal/RegisterPixKeyModal';
import { PixKey } from '@/types/Pix';


export default function PixCardGroup({keys}: {keys: PixKey[]}) {
    const [showModal, setShowModal] = useState(false);
    const [showListKeyModal, setShowListKeyModal] = useState(false);
    const [showRegisterKeyModal, setShowRegisterKeyModal] = useState(false);
    return (
        <>
            <AjustLimitPixModal show={showModal} setOpenModal={setShowModal} />
            <ListPixKeysModal show={showListKeyModal} setOpenModal={setShowListKeyModal} pixKeys={keys}/>
            <RegisterPixKeyModal show={showRegisterKeyModal} setOpenModal={setShowRegisterKeyModal} />

            <div className="space-y-5 mt-3">
                <CardGroup>
                    <MiniCard href='/transfer' icon={FaMoneyBillTransfer} title="Transferir" />
                    <MiniCard href='/payments/pix' icon={FaQrcode} title="Pagar" />
                    <MiniCard href='/deposits/pix' icon={FaMoneyBill} title="Depositar" />
                </CardGroup>

                <CardGroup>
                    <MiniCard onClick={() => setShowRegisterKeyModal(true)} icon={FaKey} title="Registrar Chave" />
                    <MiniCard onClick={() => setShowListKeyModal(true)} icon={FaListUl} title="Listar Chaves" />
                    <MiniCard onClick={() => setShowModal(true)} icon={FaSliders} title="Ajustar Limite" />
                </CardGroup>
            </div>
        </>
    )
}


function CardGroup({ children }: { children: any }) {
    return <div className="flex space-x-4 ">
        {children}
    </div>
}
