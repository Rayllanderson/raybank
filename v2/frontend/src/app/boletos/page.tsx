import React from 'react'
import { Container } from '../../components/Container';
import { MiniCard } from '../../components/cards/MiniCard';
import { Card } from '../../components/cards/Card';
import { FaBarcode, FaMoneyBill } from 'react-icons/fa6';
import { BoletoCard } from '../../components/BoletoCard';
import { findAllBoletos, findAllBoletosByStatus } from '@/services/BoletoService';
import BoletoTabs from '../../components/BoletoTabs';
import { revalidatePath } from 'next/cache';

export default async function page({ searchParams }: { searchParams: { status: string } }) {

    let boletos = await findAllBoletosByStatus(searchParams.status)

    async function onChange(boletos: any, status: string) {
        "use server"
        console.log(status)
        console.log(boletos)
        console.log(await findAllBoletosByStatus(status))
        await findAllBoletosByStatus(status)
        revalidatePath("/boletos");
    }

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
                <BoletoTabs />
                <div className="space-y-2 mt-5">
                    {boletos.length === 0 ?
                        <p>Sem boletos cadastrados</p>
                        :
                        boletos.map(boleto => <BoletoCard boleto={boleto} key={boleto.barCode} />)
                    }
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

