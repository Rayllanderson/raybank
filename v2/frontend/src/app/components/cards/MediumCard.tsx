import { Card } from 'flowbite-react'
import React from 'react'
import LinkButton from '../Buttons/LinkButton'

interface MediumCardProps {
    title: string,
    icon: any,
    href: string,
}

export default function MediumCard({title, icon, href }: MediumCardProps) {
    return (
        <Card className='p-3 w-[50%]'>
            <div className='flex justify-center items-center'>
                {icon}
            </div>
            <div className='flex flex-col justify-center items-center'>
                <p className="text-lg text-center font-mono font-semibold dark:text-white">{title}</p>
                <LinkButton href={href} gradientMonochrome='purple'> Acessar </LinkButton>
            </div>
        </Card>
    )
}
