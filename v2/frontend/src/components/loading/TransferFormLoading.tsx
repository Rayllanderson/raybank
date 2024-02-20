'use client';
import { Container } from '@/components/Container';
import React  from 'react'
import CurrencyFormLoading from '@/components/loading/CurrencyFormLoading';
import LoadingDiv from '@/components/LoadingDiv';

export default function TransferFormLoading() {
    return (
        <Container>
            <CurrencyFormLoading
                title={'Qual é o valor da transferência?'}
                subtitle={
                    <div className="flex items-center">
                        <p>Saldo disponível em conta &nbsp;</p>
                        <div className='w-14'>
                            <LoadingDiv className="rounded-md" />
                        </div>
                    </div>
                }
            />
        </Container>
    )
}