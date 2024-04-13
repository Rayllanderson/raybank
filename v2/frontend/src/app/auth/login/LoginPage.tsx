"use client";
import Logo from '@/components/Logo';
import InputWithIcon from '@/components/inputs/InputWithIcon';
import { Button } from 'flowbite-react';
import React, { ChangeEvent, FormEvent, useEffect, useState } from 'react'
import { FiLock, FiUser } from "react-icons/fi";
import { AuthFooter } from '../../../components/AuthFooter';
import { signIn } from 'next-auth/react';

type LoginInput = {
    username: string;
    password: string;
}

export const LoginPage: React.FC = () => {
    const [isVisible, setIsVisible] = useState(false);
    useEffect(() => {

        const timeout = setTimeout(() => {
            setIsVisible(true);
        }, 0.1);

        return () => clearTimeout(timeout);
    }, []);

    const handleSubmit = async (event: FormEvent) => {
        event.preventDefault();
        await signIn("credentials", {
            username: inputs.username,
            password: inputs.password,
            callbackUrl: '/'
        });
    }

    const [inputs, setInputs] = useState<LoginInput>({ username: "", password: "" });

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const name = event.target.name;
        const value = event.target.value;
        setInputs(values => ({ ...values, [name]: value }))
    }


    const appearFromRight = `transition-all duration-700 ease-in-out transform ${isVisible ? 'translate-x-0 opacity-100' : 'translate-x-[70px] opacity-0'}`;


    return (
        <div className="flex h-screen items-center justify-center dark:bg-black-1">
            <div className="flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">

                <form className={`flex flex-col max-w-md p-8 pb-0 md:pb-2  ${appearFromRight}`} onSubmit={handleSubmit}>
                    <div className="flex flex-col ">
                        <Logo />

                        <div className='flex flex-col space-y-4'>
                            <InputWithIcon type='text' 
                            placeholder='Username' 
                            icon={FiUser}
                            id="username"
                            name="username" 
                            value={inputs.username || ""}
                            onChange={handleChange}
                            required />

                            <InputWithIcon 
                            type='password' 
                            placeholder='Password' 
                            icon={FiLock}
                            id="password"
                            name="password"
                            onChange={handleChange}
                            value={inputs.password || ""}
                            required />
                        </div>

                        <div className='mt-5'>
                            <Button color='primary' fullSized type="submit">
                                <p className='text-md md:text-lg lg:text-xl'>Login</p>
                            </Button>
                        </div>
                    </div>
                </form>

                <AuthFooter text='Não tem conta?'
                    linkText='Registrar'
                    href='/auth/register'
                    className={appearFromRight}
                />
            </div>
        </div>
    )


}
