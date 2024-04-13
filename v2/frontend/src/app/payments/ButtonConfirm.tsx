'use client';
import PrimaryButton from '@/components/Buttons/PrimaryButton';
import { Button } from 'flowbite-react';
import React from 'react';

export function ButtonConfirm({ title = 'Confirmar Pagamento', onClick ,disabled = false}: { title?: string; onClick: () => void; disabled?:boolean}) {
    return <div className="mt-2 flex flex-col gap-3">
        <div className='flex mt-2'>
            <PrimaryButton className={`w-full`} onClick={onClick} disabled={disabled} loading={disabled}>
                <p>{title}</p>
            </PrimaryButton>
        </div>
    </div>;
}
