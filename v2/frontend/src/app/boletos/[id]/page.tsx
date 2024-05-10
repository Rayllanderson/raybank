import { getBoletoBeneficiaryName } from '@/app/payments/boleto/confirm/ConfirmBoletoPaymentForm'
import PrimaryButton from '@/components/Buttons/PrimaryButton'
import ClipboardCopy from '@/components/ClipboardCopy'
import ClipboardCopyIcon from '@/components/ClipboardCopyIcon'
import { Container } from '@/components/Container'
import { ReceiptListHeader } from '@/components/ReceiptListHeader'
import { ReceiptListItem } from '@/components/ReceiptListItem'
import Separator from '@/components/Separator'
import { Card } from '@/components/cards/Card'
import { findBoletoByBarCode, findBoletoOrNullByBarCode } from '@/services/BoletoService'
import { BoletoDetailsResponse } from '@/types/Boleto'
import { BoletoFormatter } from '@/utils/BoletoFormatter'
import BoletoUtils from '@/utils/BoletoUtils'
import { formatDate } from '@/utils/DateFormatter'
import { MoneyFormatter } from '@/utils/MoneyFormatter'
import { notFound } from 'next/navigation'
import React from 'react'
import { FaBarcode } from 'react-icons/fa6'
import PayBoletoButton from './PayBoletoButton'
import PreviousPageButton from '@/components/PreviousPageButton'

export default async function page({ params }: { params: { id: string } }) {
    const data: BoletoDetailsResponse | null = await findBoletoOrNullByBarCode(params.id)

    if (!data) {
        notFound()
    }

    console.log(data)

    return (
        <Container className="boleto" size="max-w-xl">
            <Card className='w-full'>
                <div className="flex space-x-3">
                    <PreviousPageButton/> 
                    <h1 className='text-lg lg:text-xl font-semibold'>Boleto Bancário</h1>
                </div>
                <div>
                    <ul>
                        <ReceiptListItem keyName='Nome Beneficiário' value={data.boleto.title} />
                        {data.beneficiary.account && <ReceiptListItem keyName='Conta Beneficiário' value={data.beneficiary.account.number} />}
                        <Separator className='mt-0' />
                        <ReceiptListItem keyName='Data Geração' value={formatDate(data.boleto.createdAt)} />
                        <ReceiptListItem keyName='Data Vencimento' value={formatDate(data.boleto.expirationDate)} />
                        <ReceiptListItem keyName='Valor' value={MoneyFormatter.format(data.boleto.value)} />
                        <ReceiptListItem keyName='Status' value={BoletoUtils.formartStatus(data.boleto.status)} />
                        <ReceiptListItem keyName='Instituição emissora' value={data.institutionIssuing.name} />
                        <Separator className='mt-0' />
                    </ul>
                </div>
                <div className='flex justify-center'>
                    <FaBarcode className='w-56 h-24' />
                </div>

                <div className='flex justify-center'>
                    <div className='max-w-lg flex space-x-1'>
                        <p className='text-wrap break-all text-primary-3 font-semibold'>{BoletoFormatter.formatBarCode(data.boleto.barCode)}</p>
                        <ClipboardCopyIcon text={data.boleto.barCode} />
                    </div>
                </div>
                {data.boleto.status === 'WAITING_PAYMENT' && <PayBoletoButton boletoData={data}/>}
            </Card>
        </Container>
    );
}
