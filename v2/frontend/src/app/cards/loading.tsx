import { Card } from '@/components/cards/Card';
import React from 'react'
import { MiniCardsLoading } from '../../components/loading/MiniCardsLoading';
import { StatementsLoading } from '@/components/loading/StatementsLoading';
import LoadingDiv from '@/components/LoadingDiv';

export default function loading() {
    return (
        <div className="cards flex w-full max-w-[23rem] md:max-w-md lg:max-w-lg flex-col">
            <Card>
                <div className='flex flex-col gap-2'>
                    <div className="flex">
                        <p className="text-lg ">Fatura Atual</p>
                    </div>

                    <div className="space-y-1">
                        <div>
                            <div className={`w-28 h-8`}>
                                <LoadingDiv className='rounded-md h-full' />
                            </div>
                        </div>
                        <div className='flex items-center space-x-3'>
                            <p className="text-md  ">Limite Disponível</p>
                            <div className={`w-32`}>
                                <LoadingDiv className='rounded-md h-full' />
                            </div>
                        </div>
                    </div>
                </div>
                <MiniCardsLoading />
            </Card>

            <div className="mt-8 p-1">
                <StatementsLoading />
            </div>
        </div>
    )
}
