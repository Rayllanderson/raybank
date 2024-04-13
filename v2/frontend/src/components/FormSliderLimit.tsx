import { useChangeCardLimitContext } from '@/context/ChangeCardLimitContext';
import { CARD_MAX_LIMIT, CARD_MIN_LIMIT, CardDetails, ChangeCardLimitFormData, changeCardLimitFormSchema } from '@/types/Card';
import { MoneyFormatter } from '@/utils/MoneyFormatter'
import { zodResolver } from '@hookform/resolvers/zod';
import { Label, RangeSlider } from 'flowbite-react'
import { signIn, useSession } from 'next-auth/react';
import React, { useEffect } from 'react'
import { useForm } from 'react-hook-form';
import Separator from './Separator';
import GradientButton from './Buttons/GradientButton';

interface Props {
    card: CardDetails
}

export default function FormSliderLimit({ card }: Props) {
    const { register, watch, handleSubmit } = useForm<ChangeCardLimitFormData>({
        resolver: zodResolver(changeCardLimitFormSchema)
    })

    const limit = watch('limit') === undefined ? card.limit : watch('limit')
    const { changeLimit, loading, error } = useChangeCardLimitContext()
    const { data: session } = useSession();

    useEffect(() => {
        if (session?.error === "RefreshAccessTokenError") {
            signIn();
        }
    }, [session]);

    const submit = async (data: ChangeCardLimitFormData) => {
        await changeLimit(data, session?.token!)
        if (!error) {
            card.limit = data.limit
        }
    };

    return (
        <form onSubmit={handleSubmit(submit)} className="flex flex-col">
            <Label className='text-lg'>Limite desejado</Label>
            <Label htmlFor="default-range" value={`${MoneyFormatter.format(limit)}`} className='text-lg' />
            <RangeSlider id="default-range"
                {...register('limit', { valueAsNumber: true })}
                disabled={loading}
                min={CARD_MIN_LIMIT} max={CARD_MAX_LIMIT} value={limit}
            />
            <Separator />
            <div className='flex justify-end mt-3 gap-3'>
                <div className="div flex-1"></div>
                <GradientButton text="Ajustar" loading={loading} />
            </div>
        </form>
    )
}
