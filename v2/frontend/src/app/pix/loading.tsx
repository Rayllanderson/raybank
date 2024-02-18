import { Container } from '@/components/Container';
import { loadingClassName } from '@/components/LoadingDiv';
import { Card } from '@/components/cards/Card';
import PixCardGroupLoading from '@/components/loading/PixCardGroupLoading';
import React from 'react'
import { FaPix } from 'react-icons/fa6';

export default function loading() {
    return (
        <Container>
            <Card className={`rounded-lg shadow-md h-80`}>
                <header className="flex items-center space-x-2">
                    <h1 className='text-xl md:text-2xl font-semibold font-mono'>Área Pix</h1>
                    <FaPix className='w-6 h-6 text-primary-2' />
                </header>
                <PixCardGroupLoading />
            </Card>
        </Container>
    )
}
