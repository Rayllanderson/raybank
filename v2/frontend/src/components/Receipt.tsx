import LightButton from '@/components/Buttons/LightButton';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import Link from 'next/link';
import React from 'react'
import { ReceiptListItem } from './ReceiptListItem';
import { ReceiptListHeader } from './ReceiptListHeader';

function Receipt({title,paymentType, amount, credit, debit, buttonTitle, buttonUrl }: { 
    title:string,
    paymentType:string,
    amount: number, 
    credit: string, 
    debit: string, 
    buttonTitle: string, 
    buttonUrl: string 
}) {
    return (
        <Container>
            <Card>
                <h1 className='text-lg lg:text-xl font-semibold'>{title}</h1>
                <ul>
                    <ReceiptListItem keyName='Valor' value={MoneyFormatter.format(amount)} />
                    <ReceiptListItem keyName='Tipo de pagamento' value={paymentType} />
                </ul>

                <ReceiptListHeader value='Destino' />
                <ul>
                    <ReceiptListItem keyName='Nome' value={credit} />
                </ul>

                <ReceiptListHeader value='Origem' />
                <ul>
                    <ReceiptListItem keyName='Nome' value={debit} />
                </ul>
                <div className='flex mt-5 space-x-2 justify-end'>
                    <Link href={buttonUrl}>
                        <LightButton>
                            {buttonTitle}
                        </LightButton>
                    </Link>
                    <Link href='/'>
                        <LightButton >
                            Voltar para Home
                        </LightButton>
                    </Link>
                </div>
            </Card>
        </Container>
    )
}

export default Receipt;
