'use client';
import { Card } from '../components/cards/Card';
import { CardMoney } from '../components/cards/CardMoney';
import { Statements } from '../components/Statements';
import React, { useRef, useState } from 'react'
import { MiniCard } from '../components/cards/MiniCard';
import { FaBarcode, FaCreditCard, FaFileInvoiceDollar, FaListUl, FaSliders } from 'react-icons/fa6';

export function WithCreditCard() {
    return <div className="cards flex w-full max-w-[23rem] md:max-w-md lg:max-w-lg flex-col">
        <Card>
            <div className='flex flex-col gap-2'>
                <div className="flex">
                    <p className="text-lg font-mono">Fatura Atual</p>
                </div>

                <div className="space-y-1">
                    <div>
                        <CardMoney value={1242.5} className='text-c-blue-1' darkColor='dark:text-c-blue-1' />
                    </div>
                    <div className='flex items-center space-x-3'>
                        <p className="text-md font-mono ">Limite Disponível</p>
                        <CardMoney size="text-md" value={5423} className='text-c-green-1 ' darkColor='dark:text-c-green-1' />
                    </div>
                </div>
            </div>
            <MiniCards />
        </Card>
        
        <div className="mt-8 p-1">
            <Statements type="card" />
        </div>
    </div>;
}

export function MiniCards() {
    const scrollableDivRef = useRef<HTMLDivElement>(null);
    const [isAutoScrolling, setIsAutoScrolling] = useState(false);

    const handleMouseMove = (e: React.MouseEvent<HTMLDivElement>) => {
        if (scrollableDivRef.current) {
            const scrollableWidth = scrollableDivRef.current.offsetWidth;
            const mousePosition = e.clientX - scrollableDivRef.current.getBoundingClientRect().left;

            const startScrollThreshold = 110;
            const endScrollThreshold = scrollableWidth - 110;

            if (mousePosition < startScrollThreshold) {
                setIsAutoScrolling(true);
                autoScroll('left');
            } else if (mousePosition > endScrollThreshold) {
                setIsAutoScrolling(true);
                autoScroll('right');
            } else {
                setIsAutoScrolling(false);
            }
        }
    };

    const autoScroll = (direction: 'left' | 'right') => {
        if (scrollableDivRef.current && isAutoScrolling) {
            const scrollStep = direction === 'left' ? -10 : 10;
            scrollableDivRef.current.scrollLeft += scrollStep;
            requestAnimationFrame(() => autoScroll(direction));
        }
    };

    const handleMouseLeave = () => {
        setIsAutoScrolling(false);
    };


    return (
        <div
            ref={scrollableDivRef}
            onMouseMove={handleMouseMove}
            onMouseLeave={handleMouseLeave}
            className='flex justify-between p-2 pl-[0.15rem] space-x-2 sm:space-x-1 overflow-x-scroll scrollbar-hide '>
            <MiniCard href='' title={'Dados Cartão'} icon={FaCreditCard} />
            <MiniCard href='/payments/card' title={'Pagar Fatura'} icon={FaBarcode} />
            <MiniCard href='' title={'Resumo Faturas'} icon={FaFileInvoiceDollar} />
            <MiniCard href='' title={'Ajustar Limite'} icon={FaSliders} />
        </div>
    )
}
