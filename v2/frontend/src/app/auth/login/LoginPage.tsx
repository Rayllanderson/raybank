"use client";
import LinkButton from '@/app/components/Buttons/LinkButton';
import { Card } from '@/app/components/cards/Card';
import InputWithIcon from '@/app/components/inputs/InputWithIcon';
import { Button } from 'flowbite-react';
import Link from 'next/link';
import React, { useState } from 'react'
import { FaBuildingColumns } from 'react-icons/fa6';
import { FiChevronRight, FiLock, FiLogIn, FiUser } from "react-icons/fi";

export const LoginPage: React.FC = () => {

    return (
        <div className="flex h-screen items-center justify-center dark:bg-black-1">
            <div className="flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">
                <Card>
                    <form className="flex flex-col max-w-md p-8 pb-0 md:pb-2 rounded ">
                        <div className="flex flex-col ">

                            <div className="flex flex-col space-y-1 mb-8">
                                <div className="p-3  rounded-sm flex justify-center">
                                    <FaBuildingColumns className='w-20 h-20 md:w-24 md:h-24 lg:w-32 lg:h-32 text-primary-1 ' />
                                </div>
                                <div className='flex justify-center'>
                                    <h1 className='text-xl text-gray-500 font-semibold font-mono'>RayBank</h1>
                                </div>
                            </div>
                            <div className='flex flex-col space-y-4'>
                                <InputWithIcon type='text' placeholder='Username' icon={FiUser} />
                                <InputWithIcon type='password' placeholder='Password' icon={FiLock} />
                            </div>

                            <div className='mt-5'>
                                <Button color='primary' fullSized>
                                    <p className='text-md md:text-lg lg:text-xl'>Login
                                    </p>
                                </Button>
                            </div>
                        </div>
                    </form>
                    <div className='flex items-center justify-center mt-5 dark:text-white text-gray-800 text-sm md:text-md lg:text-lg'>
                        <span>Nao tem conta? &nbsp;</span>

                        <Link href='/auth/register' className='flex items-center text-primary-2 hover:text-primary-1 transition ease-in duration-100'>
                            <span> Registrar </span>
                            <FiLogIn />
                        </Link>
                    </div>
                </Card>
            </div>
        </div>
    )
}
