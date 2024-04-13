import React, { InputHTMLAttributes, forwardRef } from 'react'
import InputText, { InputTextProps } from './InputText'

const InputNumeric: React.ForwardRefRenderFunction<HTMLInputElement, InputTextProps> = ({...props }, ref) => {
    return (
        <InputText
            {...props}
            inputMode="numeric"
            pattern="[0-9]*"
            ref={ref}
        />
    )
}

export default forwardRef(InputNumeric);