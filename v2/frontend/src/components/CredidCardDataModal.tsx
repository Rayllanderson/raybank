'use client';
import React, { useEffect, useState } from 'react';
import { Modal, ModalBody, ModalHeader } from 'flowbite-react';
import CreditCard from '@/components/CreditCard';
import { signIn, useSession } from 'next-auth/react';
import { CardDetails } from '@/types/Card';
import { getCreditCardWithSensitiveData } from '@/services/CardService';
import toast from 'react-hot-toast';
import { ApiErrorException } from '@/types/Error';

export function CredidCardDataModal({ show, setOpenModal }: { show: boolean; setOpenModal: (v: boolean) => void; }) {

    const { data: session } = useSession();
    const [card, setCard] = useState<CardDetails>()
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        if (session?.error === "RefreshAccessTokenError") {
            signIn();
        }

        async function fetchCardDetails() {
            try {
                setLoading(true)
                const card = await getCreditCardWithSensitiveData(session?.token!);
                setCard(card);
                setLoading(false)
            } catch (error) {
                if (error instanceof ApiErrorException) {
                    toast.error('Erro ao buscar detalhes do cartão');
                }

                setLoading(false)
            }
        }

        if (show === true && !card) {
            fetchCardDetails()
        }
    }, [show, card, session]);

    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size={'lg'}>
            <ModalHeader>Dados do Cartão de Crédito</ModalHeader>
            <ModalBody>
                <CreditCard number={card?.number!} cvv={card?.securityCode!} expiryDate={card?.expiryDate!} name={session?.user?.name}
                    loading={loading && !card} />
            </ModalBody>
        </Modal>
    );
}
