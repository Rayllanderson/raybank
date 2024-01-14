import PreviousPageButton from '@/app/components/PreviousPageButton';
import { MoneyFormatter } from '@/app/utils/MoneyFormatter';
import React from 'react';

export function ConfirmTransactionHeader({ title, amount, beneficiaryName }: { title:string, amount: number; beneficiaryName: string; }) {
    return <header className="flex flex-col gap-3">
        <div>
            <PreviousPageButton />
        </div>
        <div className="text-start">
            <h1 className="text-lg md:text-xl lg:text-2xl font-semibold">{title}</h1>
            <h1 className="text-3xl md:text-4xl font-semibold text-primary-2">{MoneyFormatter.format(amount)}</h1>
            <p className='text-md md:text-lg text-gray-500 dark:text-gray-400'>para: {beneficiaryName}</p>
        </div>
    </header>;
}
