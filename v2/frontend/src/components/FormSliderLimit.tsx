import { MoneyFormatter } from '@/utils/MoneyFormatter'
import { Label, RangeSlider } from 'flowbite-react'
import React from 'react'


interface Props {
    limit: number,
    handleSliderChange: (event: React.ChangeEvent<HTMLInputElement>) => void
}

export default function FormSliderLimit({limit, handleSliderChange}: Props) {
    return (
        <div className="flex flex-col">
            <Label className='text-lg'>Limite desejado</Label>
            <Label htmlFor="default-range" value={`${MoneyFormatter.format(limit)}`} className='text-lg' />
            <RangeSlider id="default-range" min="0" max="15000" value={limit} onChange={handleSliderChange} />
        </div>
    )
}
