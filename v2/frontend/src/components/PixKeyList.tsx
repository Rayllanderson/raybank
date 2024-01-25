import React from 'react';
import { PixKey } from '@/types/Pix';
import Separator from './Separator';
import { FaDice, FaMobileScreen, FaRegEnvelope, FaRegUser, FaShield, FaTrash } from 'react-icons/fa6';
import { pixTypeTranslationsStrings } from '@/utils/PixKeyUtil';
import ButtonRemovePixKey from './Buttons/ButtonRemovePixKey';

export function PixKeyListElement({ pixKey }: { pixKey: PixKey }): React.JSX.Element {
    const Icon = getIconByPixType(pixKey);

    return <>
        <div className={`flex justify-between p-1`}>
            <div className='flex space-x-3 items-center'>
                <Icon className='h-5 w-5' />
                <div className=''>
                    <p className='font-semibold'>{pixTypeTranslationsStrings[pixKey.type]}</p>
                    <p className='font-light'>{pixKey.key}</p>
                </div>
            </div>
            <div className='flex items-center space-x-2'>
                <ButtonRemovePixKey pixKey={pixKey} />
            </div>
        </div>
        <Separator />
    </>;
}

function getIconByPixType(pixKey: PixKey) {
    if (pixKey.type === 'EMAIL')
        return FaRegEnvelope;
    if (pixKey.type === 'RANDOM')
        return FaDice;
    if (pixKey.type === 'PHONE')
        return FaMobileScreen;
    if (pixKey.type === 'CPF')
        return FaRegUser;
}
