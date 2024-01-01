"use client";
import Logo from '@/app/components/Logo';
import InputWithIcon from '@/app/components/inputs/InputWithIcon';
import { Button } from 'flowbite-react';
import React, { useEffect, useState } from 'react'
import { FiLock, FiLogIn, FiUser } from "react-icons/fi";
import { AuthFooter } from '../../components/AuthFooter';

export const LoginPage: React.FC = () => {
    const [isVisible, setIsVisible] = useState(false);
    useEffect(() => {

        const timeout = setTimeout(() => {
            setIsVisible(true);
        }, 0.1);

        return () => clearTimeout(timeout);
    }, []);


    const appearFromRight = `transition-all duration-700 ease-in-out transform ${isVisible ? 'translate-x-0 opacity-100' : 'translate-x-[70px] opacity-0'}`;


    return (
        <div className="flex h-screen items-center justify-center dark:bg-black-1">
            <div className="flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">

                <form className={`flex flex-col max-w-md p-8 pb-0 md:pb-2  ${appearFromRight}`}>
                    <div className="flex flex-col ">
                        <Logo />

                        <div className='flex flex-col space-y-4'>
                            <InputWithIcon type='text' placeholder='Username' icon={FiUser} />
                            <InputWithIcon type='password' placeholder='Password' icon={FiLock} />
                        </div>

                        <div className='mt-5'>
                            <Button color='primary' fullSized>
                                <p className='text-md md:text-lg lg:text-xl'>Login</p>
                            </Button>
                        </div>
                    </div>
                </form>

                <AuthFooter text='Nao tem conta?'
                    linkText='Registrar'
                    href='/auth/register'
                    className={appearFromRight}
                />
            </div>
        </div>
    )


}
