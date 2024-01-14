'use client';
import { Button } from 'flowbite-react';
import React from 'react';

export function ButtonConfirm({ title = 'Confirmar Pagamento', onClick }: { title?: string; onClick: () => void; }) {
    return <div className="mt-2 flex flex-col gap-3">
        <div className='flex mt-2'>
            <Button color='primary' className={`w-full`}>
                <p>{title}</p>
            </Button>
        </div>
    </div>;
}
