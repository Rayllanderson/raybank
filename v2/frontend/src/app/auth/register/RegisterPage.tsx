"use client";
import { AuthFooter } from '@/app/components/AuthFooter';
import Logo from '@/app/components/Logo';
import { Card } from '@/app/components/cards/Card';
import InputWithIcon from '@/app/components/inputs/InputWithIcon';
import { Button } from 'flowbite-react';
import React, { useEffect, useState } from 'react'
import { FiLock, FiLogIn, FiUser } from "react-icons/fi";


export default function RegisterPage() {

    const [isVisible, setIsVisible] = useState(false);
    useEffect(() => {

        const timeout = setTimeout(() => {
            setIsVisible(true);
        }, 0.1);

        return () => clearTimeout(timeout);
    }, []);


    const appearFromLeft = `transition-all duration-700 ease-in-out transform ${isVisible ? 'translate-x-0 opacity-100' : 'translate-x-[-70px] opacity-0'}`;

    return (
        <div className={`flex h-screen items-center justify-center dark:bg-black-1`}>
            <div className="flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">
                <form className={`flex flex-col max-w-md p-8 pb-0 md:pb-2 rounded ${appearFromLeft}`}>
                    <div className="flex flex-col ">

                        <Logo />

                        <div className='flex flex-col space-y-4'>
                            <InputWithIcon type='text' placeholder='Nome' icon={FiUser} />
                            <InputWithIcon type='text' placeholder='Username' icon={FiUser} />
                            <InputWithIcon type='password' placeholder='Password' icon={FiLock} />
                        </div>

                        <div className='mt-5'>
                            <Button color='primary' fullSized>
                                <p className='text-md md:text-lg lg:text-xl'>Registrar
                                </p>
                            </Button>
                        </div>
                    </div>
                </form>

                <AuthFooter text='Já tem uma conta?'
                    linkText='Login' href='/auth/login'
                    className={appearFromLeft} />
            </div>
        </div>
    )
}