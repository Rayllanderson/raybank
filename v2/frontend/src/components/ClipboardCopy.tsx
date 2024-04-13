'use client';
import React, { useState } from 'react';
import copy from 'clipboard-copy';
import { LuCopy, LuCopyCheck } from "react-icons/lu";
import PrimaryButton from '@/components/Buttons/PrimaryButton';

const ClipboardCopy = ({ text }: { text: string; }) => {
    const [copied, setCopied] = useState(false);

    const handleCopy = async () => {
        await copy(text);
        setCopied(true);
        setTimeout(() => setCopied(false), 2500);
    };

    return (
        <div className="">
            <PrimaryButton
                onClick={handleCopy}
                title='copiar'
                className="transition-all duration-300  ease-in-out active:scale-[.95]">
                {copied ?
                    <div className="flex items-center space-x-2">
                        <p>Copiado</p>
                        <LuCopyCheck className="h-4 w-4 opacity-100 text-green-400" />
                    </div>
                    :
                    <div className="flex items-center space-x-2">
                        <p>Copiar código</p>
                        <LuCopy className="h-4 w-4 opacity-100" />
                    </div>
                }
            </PrimaryButton>
        </div>
    );
};

export default ClipboardCopy;