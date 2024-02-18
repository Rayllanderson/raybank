'use client';
import React from 'react';
import { FaBarcode, FaCreditCard, FaFileInvoiceDollar, FaSliders } from 'react-icons/fa6';
import { MiniCardLoading } from '@/components/loading/MiniCardLoading';

export function MiniCardsLoading() {
    return (
        <>
            <div
                className='flex justify-between p-2 pl-[0.15rem] space-x-2 sm:space-x-1 overflow-x-scroll scrollbar-hide '>
                <MiniCardLoading title={'Dados Cartão'} icon={FaCreditCard} />
                <MiniCardLoading href='/payments/invoice' title={'Pagar Fatura'} icon={FaBarcode} />
                <MiniCardLoading href='/cards/invoices' title={'Resumo Faturas'} icon={FaFileInvoiceDollar} />
                <MiniCardLoading title={'Ajustar Limite'} icon={FaSliders} />
            </div>
        </>
    );
}
