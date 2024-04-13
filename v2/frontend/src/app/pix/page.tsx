import React from 'react'
import { Container } from '../../components/Container';
import { Card } from '../../components/cards/Card';
import { FaPix } from 'react-icons/fa6';
import PixCardGroup from './PixCardGroup';
import { PixService } from '@/services/PixService';
import { PixKey } from '@/types/Pix';


export default async function page() {

    const pixKeys: PixKey[] = await PixService.findAllPixKey()


    return (
        <Container>
            <Card >
                <header className="flex items-center space-x-2">
                    <h1 className='text-xl md:text-2xl font-semibold font-mono'>Área Pix</h1>
                    <FaPix className='w-6 h-6 text-primary-2'/>
                </header>
                <PixCardGroup keys={pixKeys} />
            </Card>
        </Container>
    )

}

