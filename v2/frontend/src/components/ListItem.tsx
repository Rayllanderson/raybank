import React from 'react'
import { FaAngleRight } from 'react-icons/fa6';
import Separator from './Separator';
import Link from 'next/link';

interface Props {
    title: string;
    subtitle?: string;
    icon: any;
    type?: 'link' | 'button'
    href?: string;
    onClick?: () => void;
    withSeparator?: boolean;
}

export default function ListItem({ type = 'button', title, subtitle, href, withSeparator = false, icon: Icon, onClick }: Props) {
    return (
        <>
            {type === 'button' ? (
                <button title={''} onClick={onClick} className=''>
                    <DefaultComponent title={title} subtitle={subtitle} icon={Icon} onClick={onClick} withSeparator={withSeparator} />
                </button>
            )
                :
                (<Link href={href || ''}>
                    <DefaultComponent title={title} subtitle={subtitle} icon={Icon} withSeparator={withSeparator} />
                </Link>)
            }
        </>
    )
}

function DefaultComponent({ title, subtitle, href, withSeparator = false, icon: Icon, onClick }: Props) {
    return (
        <div className='w-full hover:scale-[1.03] transition-transform'>
            <div className='flex justify-between items-center'>
                <div className="flex justify-between items-center space-x-3">
                    <div>
                        <Icon className='h-6 w-6 text-gray-800 dark:text-gray-300' />
                    </div>
                    <div className='flex flex-col max-w-xs items-start justify-center text-start'>
                        <p className='text-md md:text-lg font-medium'>{title}</p>
                        <p className='text-sm md:text-md text-gray-500 text-start'>{subtitle}</p>
                    </div>
                </div>
                <div>
                    <FaAngleRight />
                </div>
            </div>
            {withSeparator && <Separator type='thin' />}
        </div>
    )
}

