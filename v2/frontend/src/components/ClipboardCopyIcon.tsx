'use client';
import React, { useState } from 'react';
import copy from 'clipboard-copy';
import { LuCopy, LuCopyCheck } from "react-icons/lu";
import PrimaryButton from '@/components/Buttons/PrimaryButton';

const ClipboardCopyIcon = ({ text }: { text: string; }) => {
    const [copied, setCopied] = useState(false);

    const handleCopy = async () => {
        await copy(text);
        setCopied(true);
        setTimeout(() => setCopied(false), 2500);
    };

    const defaultSize = 'h-[1.10rem] w-[1.10rem]'
    return (
        <div>
            <button
                onClick={handleCopy}
                title='copiar'
                className="transition-all duration-300  ease-in-out active:scale-[.95] bg-transparent">
                {copied ?
                    <div className="flex items-center space-x-2">
                        <LuCopyCheck className={`${defaultSize} opacity-100 text-green-400`} />
                    </div>
                    :
                    <div className="flex items-center space-x-2">
                        <LuCopy className={`${defaultSize} opacity-100`} />
                    </div>
                }
            </button>
        </div>
    );
};

export default ClipboardCopyIcon;