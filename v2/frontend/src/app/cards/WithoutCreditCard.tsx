'use client';

import { Card } from '../../components/cards/Card';
import React, { useEffect } from 'react'
import { Container } from '../../components/Container';
import { Button, Label, RangeSlider, Select } from 'flowbite-react';
import { MoneyFormatter } from '../../utils/MoneyFormatter';
import { useForm } from "react-hook-form"
import { zodResolver } from '@hookform/resolvers/zod';
import toast from 'react-hot-toast';
import { CARD_MAX_LIMIT, CARD_MIN_LIMIT, CreateCardFormData, DUE_DAYS, createCardFormSchema } from '@/types/Card';
import { useRouter } from 'next/navigation';
import { signIn, useSession } from 'next-auth/react';
import { useCardContext } from '@/context/CreateCardContext ';
import { ImSpinner2 } from "react-icons/im";

export function WithoutCreditCard() {
    const { data: session } = useSession();

    useEffect(() => {
      if (session?.error === "RefreshAccessTokenError") {
        signIn(); 
      }
    }, [session]);

    const { createCard, error, loading } = useCardContext();

    const router = useRouter();
    const { register, handleSubmit, watch, formState } = useForm<CreateCardFormData>({ resolver: zodResolver(createCardFormSchema) })
    const limit = watch('limit') === undefined ? 7500 : watch('limit')

    async function onSubmit(data: CreateCardFormData) {
        await createCard(data, session?.token!);
        if (!error)
            router.replace('/cards');
    }

    if (!formState.isValid) {
        if (formState.errors.due_day) {
            toast.error("Data de Vencimento inválida. Valores válidos são: " + Object.values(DUE_DAYS));
        }
        if (formState.errors.limit) {
            toast.error(`Limite precisa estar entre ${CARD_MIN_LIMIT} e ${CARD_MAX_LIMIT}`);
        }
    }

    return (

        <>
            <Container >

                <div className='flex flex-col space-y-2 items-center text-center'>
                    <h1 className='text-2xl font-semibold font-mono'>Você ainda não tem um cartão de crédito?</h1>
                    <h1 className='text-xl'>Aproveite agora para solicitar o seu gratuitamente e tenha acesso a benefícios exclusivos!</h1>
                </div>

                <Card className='mt-8'>
                    <header className='flex space-x-1 items-center'>
                        <h1 className='text-xl font-semibold'> Solicite seu cartão de crédito! </h1>
                    </header>


                    <form className='flex flex-col' onSubmit={handleSubmit(onSubmit)}>

                        <div className="space-y-2">
                            <div>
                                <div className="mb-2 block">
                                    <Label htmlFor="dueDate" value="Dia do vencimento da fatura" className='text-lg' />
                                </div>
                                <div className="max-w-[30%] md:max-w-[20%]">
                                    <Select id="dueDate" required color='default'
                                        {...register('due_day', { valueAsNumber: true })}>
                                        {Object.values(DUE_DAYS).map(date => {
                                            return <option key={date} value={date}>{date}</option>
                                        })}
                                    </Select>
                                </div>
                            </div>

                            <div className="flex flex-col">
                                <Label className='text-lg'>Limite desejado</Label>
                                <Label htmlFor="default-range" value={`${MoneyFormatter.format(limit)}`} className='text-lg' />
                                <RangeSlider {...register('limit', { valueAsNumber: true })}
                                    id="default-range" min={CARD_MIN_LIMIT} max={CARD_MAX_LIMIT} value={limit} />
                            </div>
                        </div>
                        <Button disabled={loading} type='submit'
                            gradientMonochrome={'purple'} className='mt-5'> {loading ? <ImSpinner2 className="h-5 w-5 animate-spin" /> : 'Solicitar'} </Button>
                    </form>
                </Card>
            </Container>
        </>
    )
}