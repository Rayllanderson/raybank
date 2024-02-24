import LightButton from '@/components/Buttons/LightButton';
import { Container } from '@/components/Container';
import Separator from '@/components/Separator';
import { Card } from '@/components/cards/Card';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import Link from 'next/link';
import React from 'react'
import { FaRegMoneyBill1 } from 'react-icons/fa6';

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
                    <ListItem keyName='Valor' value={MoneyFormatter.format(amount)} />
                    <ListItem keyName='Tipo de pagamento' value={paymentType} />
                </ul>

                <ListHeader value='Destino' />
                <ul>
                    <ListItem keyName='Nome' value={credit} />
                </ul>

                <ListHeader value='Origem' />
                <ul>
                    <ListItem keyName='Nome' value={debit} />
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

function ListHeader({ value }: { value: 'Origem' | 'Destino' }) {
    return (
        <>
            <div className={`mt-2 mb-[-5px] flex items-center space-x-1 ${value === 'Destino' ? 'text-green-500' : `text-red-500`}`}>
                <div>
                    <FaRegMoneyBill1 className='w-5 h-5' />
                </div>
                <p className='text-lg font-semibold'>{value}</p>
            </div>
            <Separator className='mt-0' />
        </>)
}

function ListItem({ keyName, value }: { keyName: string, value: any }) {
    return <li className='flex justify-between'>
        <div className='font-semibold'>{keyName}</div>
        <div>{value}</div>
    </li>
}

export default Receipt;
