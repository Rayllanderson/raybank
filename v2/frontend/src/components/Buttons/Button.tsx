import { Button as FButton } from 'flowbite-react'
import React, { ButtonHTMLAttributes, ElementType } from 'react'
import { ImSpinner2 } from 'react-icons/im';

interface Props extends ButtonHTMLAttributes<HTMLButtonElement>{
    loading?: boolean;
    children?: any
    className?:string;
}

export default function Button({loading, children,className,...props}: Props) {
    return (
        <FButton {...props} className={`w-full ${className}`}>
            {loading ? <div className='animate-spin'><ImSpinner2 className="h-5 w-5" /></div> : children}
        </FButton>
    )
}