'use client';
import { Card } from '../components/cards/Card';
import { CardMoney } from '../components/cards/CardMoney';
import { Statements } from '../components/Statements';
import React, { useRef, useState } from 'react'
import { MiniCard } from '../components/cards/MiniCard';
import { FaBarcode, FaCcMastercard, FaCreditCard, FaFileInvoiceDollar, FaListUl } from 'react-icons/fa6';
import { Container } from '../components/Container';
import { Button, Label, RangeSlider, Select } from 'flowbite-react';
import { MoneyFormatter } from '../utils/MoneyFormatter';
import CardHeader from '../components/cards/CardHeader';

export function WithoutCreditCard() {
    const [limit, setLimit] = useState(7500);

    const handleSliderChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setLimit(Number(event.target.value));
    };


    return (
        <Container className="">
            <div className='flex flex-col space-y-2 items-center text-center'>
                <h1 className='text-2xl font-semibold font-mono'>Você ainda não tem um cartão de crédito?</h1>
                <h1 className='text-xl'>Aproveite agora para solicitar o seu gratuitamente e tenha acesso a benefícios exclusivos!</h1>
            </div>

            <Card className='mt-8'>
                <header className='flex space-x-1 items-center'>
                    <h1 className='text-xl font-semibold'> Solicite seu cartão de crédito! </h1>
                </header>


                <form className='flex flex-col '>

                    <div  className="space-y-2">
                        <div>
                            <div className="mb-2 block">
                                <Label htmlFor="dueDate" value="Dia do vencimento da fatura" className='text-lg' />
                            </div>
                            <div className="max-w-[30%] md:max-w-[20%]">
                                <Select id="dueDate" required color='default'>
                                    <option>06</option>
                                    <option>12</option>
                                    <option>18</option>
                                    <option>24</option>
                                </Select>
                            </div>
                        </div>

                        <div className="flex flex-col">
                            <Label className='text-lg'>Limite desejado</Label>
                            <Label htmlFor="default-range" value={`${MoneyFormatter.format(limit)}`} className='text-lg' />
                            <RangeSlider id="default-range" min="0" max="15000" value={limit} onChange={handleSliderChange} />
                        </div>
                    </div>
                        <Button gradientMonochrome={'purple'} className='mt-5'> Solicitar </Button>
                </form>
            </Card>
        </Container>
    )
}