'use client';
import React, { useRef, useState } from 'react';
import { MiniCard } from '../../components/cards/MiniCard';
import { FaBarcode, FaCreditCard, FaFileInvoiceDollar, FaSliders } from 'react-icons/fa6';
import { CredidCardDataModal } from '@/components/CredidCardDataModal';
import { AjustLimitCardModal } from '@/components/modal/AjustLimitCardModal';
import { CardDetails } from '@/types/Card';

export function MiniCards({card}: {card: CardDetails}) {
    const scrollableDivRef = useRef<HTMLDivElement>(null);
    const [isAutoScrolling, setIsAutoScrolling] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [showLimitModal, setShowLimitModal] = useState(false);

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
        <>
            <CredidCardDataModal show={showModal} setOpenModal={setShowModal} />
            <AjustLimitCardModal show={showLimitModal} setOpenModal={setShowLimitModal} card={card} />
            <div
                ref={scrollableDivRef}
                onMouseMove={handleMouseMove}
                onMouseLeave={handleMouseLeave}
                className='flex justify-between p-2 pl-[0.15rem] space-x-2 sm:space-x-1 overflow-x-scroll scrollbar-hide '>
                <MiniCard onClick={() => setShowModal(true)} title={'Dados Cartão'} icon={FaCreditCard} />
                <MiniCard href='/payments/invoice' title={'Pagar Fatura'} icon={FaBarcode} />
                <MiniCard href='/cards/invoices' title={'Resumo Faturas'} icon={FaFileInvoiceDollar} />
                <MiniCard onClick={() => setShowLimitModal(true)}  title={'Ajustar Limite'} icon={FaSliders} />
            </div>
        </>
    );
}
