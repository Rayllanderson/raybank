import { Button } from 'flowbite-react'
import React from 'react'
import { ImSpinner2 } from 'react-icons/im'

interface Props {
    loading: boolean
    text: string,
    onClick?:()=>void
}


export default function GradientButton({ loading, text, onClick }: Props) {
    return (
        <Button disabled={loading} type='submit' gradientMonochrome={'purple'} className='w-full' onClick={onClick}>
            {loading ? <ImSpinner2 className="h-5 w-5  animate-spin" /> : text}
        </Button>
    )
}
