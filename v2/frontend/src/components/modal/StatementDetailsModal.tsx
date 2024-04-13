import { Statement } from '@/types/Statement';
import { capitalizeFirstLetter } from '@/utils/StringUtils';
import { Modal, ModalBody, ModalHeader } from 'flowbite-react';
import React from 'react'
import { ReceiptBody } from '../ReceiptBody';
import Separator from '../Separator';
import { formatStatementMethod } from '@/utils/StatementUtil';

export default function StatementDetailsModal({ statement, show, setOpenModal }: { statement: Statement | null, show: boolean; setOpenModal: (v: boolean) => void; }) {
    return (
        <>
            {statement &&
                <Modal show={show} onClose={() => setOpenModal(false)} size='md'>
                    <ModalHeader>{statement?.title == null ? capitalizeFirstLetter(statement?.description) : capitalizeFirstLetter(statement?.title)}</ModalHeader>
                    <ModalBody className='flex flex-col justify-start'>
                        <ReceiptBody
                            title={''}
                            amount={statement?.amount!}
                            credit={statement?.credit.name!}
                            debit={statement?.debit.name!}
                            paymentType={formatStatementMethod(statement?.method!)}
                            receiptListItem={statement?.installmentPlan && [{key:'Parcelamento', value: statement?.installmentPlan!.installments + 'x'}]}
                        />
                        {statement?.message &&
                            <div className='mt-3'>
                                <Separator />
                                <label htmlFor="description" className="">Mensagem</label>
                                <input id='description' disabled className="md:text-lg p-2 rounded-md w-full bg-gray-200 text-gray-500 dark:bg-black-3" value={statement?.message} />
                            </div>
                        }
                    </ModalBody>
                </Modal>
            }
        </>
    )
}
