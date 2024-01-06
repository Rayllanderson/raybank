import React from 'react'
import { Container } from '../components/Container';
import { MiniCard } from '../components/cards/MiniCard';
import { Card } from '../components/cards/Card';
import { FaKey, FaListUl, FaMoneyBill, FaMoneyBillTransfer, FaPix, FaQrcode, FaSliders } from 'react-icons/fa6';


export default function page() {
    return (
        <Container>
            <Card >
                <header className="flex items-center space-x-2">
                    <h1 className='text-xl md:text-2xl font-semibold font-mono'>Área Pix</h1>
                    <FaPix className='w-6 h-6 text-primary-2'/>
                </header>

                <div className="space-y-5 mt-3">
                    <CardGroup>
                        <MiniCard icon={FaMoneyBillTransfer} title="Transferir" />
                        <MiniCard icon={FaQrcode} title="Pagar" />
                        <MiniCard icon={FaMoneyBill} title="Depositar" />
                    </CardGroup>

                    <CardGroup>
                        <MiniCard icon={FaKey} title="Registrar Chave" />
                        <MiniCard icon={FaListUl} title="Listar Chaves" />
                        <MiniCard icon={FaSliders} title="Ajustar Limite" />
                    </CardGroup>
                </div>

            </Card>
        </Container>
    )

}

function CardGroup({ children }: {children: any}) {
    return <div className="flex space-x-4 ">
        {children}
    </div>
}

