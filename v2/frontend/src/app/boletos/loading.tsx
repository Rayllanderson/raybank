import React from 'react'
import { Container } from '../../components/Container';
import { MiniCard } from '../../components/cards/MiniCard';
import { Card } from '../../components/cards/Card';
import { FaBarcode, FaMoneyBill } from 'react-icons/fa6';
import { BoletoCardLoading } from '@/components/loading/BoletoCardLoading';

export default function loading() {
    return (
        <Container>
            <Card >
                <header className="flex items-center">
                    <h1 className='text-xl md:text-2xl font-semibold font-mono'>Boletos</h1>
                </header>

                <div className="space-y-5 mt-3">
                    <CardGroup>
                        <MiniCard href='/payments/boleto' icon={FaBarcode} title="Pagar" className='w-32 h-28 md:w-[8.5rem] lg:w-36' />
                        <MiniCard href='/deposits/boleto' icon={FaMoneyBill} title="Depositar" className='w-32 h-28 md:w-[8.5rem] lg:w-36' />
                    </CardGroup>
                </div>
            </Card>

            <div className='mt-8 p-1'>
                <h1 className="text-xl font-semibold font-mono">Meus Boletos</h1>
                <div className="space-y-2 mt-5">
                    {[1, 2, 3, 4, 5].map(boleto => <BoletoCardLoading key={boleto} />)}
                </div>
            </div>
        </Container>
    )
}

function CardGroup({ children }: { children: any }) {
    return <div className="flex justify-evenly">
        {children}
    </div>
}