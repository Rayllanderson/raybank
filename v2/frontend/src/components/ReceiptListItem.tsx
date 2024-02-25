import React from 'react';


export function ReceiptListItem({ keyName, value }: { keyName: string; value: any; }) {
    return <li className='flex justify-between'>
        <div className='font-semibold'>{keyName}</div>
        <div>{value}</div>
    </li>;
}
