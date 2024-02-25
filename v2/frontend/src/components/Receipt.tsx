import LightButton from '@/components/Buttons/LightButton';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import Link from 'next/link';
import React from 'react'
import { ReceiptBody } from './ReceiptBody';

function Receipt({ title, paymentType, amount, credit, debit, buttonTitle, buttonUrl }: {
    title: string,
    paymentType: string,
    amount: number,
    credit: string,
    debit: string,
    buttonTitle: string,
    buttonUrl: string
}) {
    return (
        <Container>
            <Card>
                <ReceiptBody
                    title={title}
                    amount={amount}
                    credit={credit}
                    debit={debit}
                    paymentType={paymentType}
                />

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
