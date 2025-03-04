import { Card } from '../../components/cards/Card';
import { CardMoney } from '../../components/cards/CardMoney';
import React from 'react'
import { MiniCards } from './MiniCards';
import { CardDetails } from '@/types/Card';
import { Statement, Statements as StatementsArray} from '@/types/Statement';
import { getAllCardStatements } from '@/services/StatementService';
import { Statements } from '@/components/Statements';
import { Page } from '@/types/Page';

export async function WithCreditCard({card}: {card: CardDetails}) {
    return <div className="cards flex w-full max-w-[22.5rem] md:max-w-[23rem] md:max-w-md lg:max-w-lg flex-col">
        <Card>
            <div className='flex flex-col gap-2'>
                <div className="flex">
                    <p className="text-lg ">Fatura Atual</p>
                </div>

                <div className="space-y-1">
                    <div>
                        <CardMoney value={card.invoiceValue!} className='text-c-blue-1' darkColor='dark:text-c-blue-1' />
                    </div>
                    <div className='flex items-center space-x-3'>
                        <p className="text-md  ">Limite Disponível</p>
                        <CardMoney size="text-md" value={card.availableLimit} className='text-c-green-1 ' darkColor='dark:text-c-green-1' />
                    </div>
                </div>
            </div>
            <MiniCards card={card}/>
        </Card>

        <div className="mt-4 lg:mt-8 p-1">
            <Statements type="card"/>
        </div>
    </div>;
}