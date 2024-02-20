'use client';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import InputText from '@/components/inputs/InputText';
import { useTransferTransactionContext } from '@/context/TransferContext';
import { ConfirmTransactionHeader } from '@/components/ConfirmTransactionHeader';
import { Button } from 'flowbite-react';
import { useRouter } from 'next/navigation';
import React, { useCallback, useEffect, useRef } from 'react'
import toast from 'react-hot-toast';
import { ImSpinner2 } from 'react-icons/im';
import PrimaryButton from '@/components/Buttons/PrimaryButton';
import { ConfirmTransactionHeaderLoading } from '@/components/loading/ConfirmTransactionHeaderLoading';


export default function ConfirmTransferFormLoading() {
    return (
        <>
            <Container>
                <Card >
                    <ConfirmTransactionHeaderLoading title="Transferindo" />

                    <div className="mt-2 flex flex-col gap-3">
                        <InputText placeholder='Mensagem (opcional)' disabled />

                        <div className='flex mt-2'>
                            <PrimaryButton disabled>
                                <p>Confirmar Transferência</p>
                            </PrimaryButton>
                        </div>
                    </div>
                </Card>
            </Container>
        </>
    )
}