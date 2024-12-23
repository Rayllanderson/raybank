import { MoneyFormatter } from '@/utils/MoneyFormatter';
import React from 'react';
import { ReceiptListItem } from './ReceiptListItem';
import { ReceiptListHeader } from './ReceiptListHeader';
import { formatStatementMoment } from '@/utils/StatementUtil';

export function ReceiptBody({ receiptListItem, moment, title, paymentType, amount, credit, debit }: {
    title: string;
    paymentType: string;
    moment?:string;
    amount: number;
    credit: string;
    debit: string;
    receiptListItem?: { key: string, value: string }[]
}) {
    return (
        <>
            <h1 className='text-lg lg:text-xl font-semibold'>{title}</h1>
            <ul>
                <ReceiptListItem keyName='Valor' value={MoneyFormatter.format(amount)} />
                <ReceiptListItem keyName='Tipo de transação' value={paymentType} />
                {receiptListItem?.map((receipt) => <ReceiptListItem key={receipt.key} keyName={receipt.key} value={receipt.value} />)}
                {moment && <ReceiptListItem keyName={formatStatementMoment(moment)} value={''} /> }
            </ul>

            <ReceiptListHeader value='Destino' />
            <ul>
                <ReceiptListItem keyName='Nome' value={credit} />
            </ul>

            <ReceiptListHeader value='Origem' />
            <ul>
                <ReceiptListItem keyName='Nome' value={debit} />
            </ul>

        </>
    );
}
