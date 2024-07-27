'use client'
import { Avatar } from 'flowbite-react'
import React from 'react'
import { ImSpinner2 } from 'react-icons/im'

export default function AvatarLoading({size, bordered,className}: {className?:string, size?: string, bordered?: boolean}) {
    return (
        <div className="relative inline-block">
            <Avatar size={size} rounded bordered={bordered} color="primary" className={className}/>
            <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-50 rounded-full z-10">
                <div className='animate-spin'>
                    <ImSpinner2 className="h-5 w-5 text-white" />
                </div>
            </div>
        </div>
    )
}
