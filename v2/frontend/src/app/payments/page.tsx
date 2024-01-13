import React from 'react'
import { Container } from '../components/Container';
import { MiniCard } from '../components/cards/MiniCard';
import { Card } from '../components/cards/Card';
import { FaBarcode, FaCreditCard, FaMoneyBill, FaPix, FaQrcode } from 'react-icons/fa6';

export default function page() {
    return (
        <Container>
            <Card >
                <header className="flex items-center">
                    <h1 className='text-xl md:text-2xl font-semibold font-mono'>Opcões de Pagamento</h1>
                </header>

                <div className="space-y-5 mt-3">
                    <CardGroup>
                        <MiniCard href='/payments/pix' icon={FaPix} title="Pix Copia e Cola" />
                        <MiniCard href='' icon={FaCreditCard} title="Fatura do Cartão" />
                        <MiniCard href='' icon={FaBarcode} title="Boleto" />
                    </CardGroup>
                </div>
            </Card>

        </Container>
    )
}

function CardGroup({ children }: { children: any }) {
    return <div className="flex justify-evenly">
        {children}
    </div>
}

