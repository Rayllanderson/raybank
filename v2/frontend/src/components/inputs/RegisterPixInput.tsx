import React, { HtmlHTMLAttributes, InputHTMLAttributes } from 'react'
import InputNumeric from './InputNumeric'
import InputText, { InputTextProps } from './InputText'

interface Props extends InputHTMLAttributes<HTMLInputElement>{
    onChange: (event: any) => void
}

export function RegisterPhoneInput({onChange, ...props}: Props) {
    return (
        <InputNumeric placeholder='Numero do telefone' sizing='sm' onChange={onChange} {... props}/>
    )
}

export function RegisterCpfInput({onChange,...props}: Props) {
    return (
        <InputNumeric placeholder='Digite o CPF' sizing='sm' onChange={onChange} {... props}/>
    )
}

export function RegisterEmailInput({onChange,...props}: Props) {
    return (
        <InputText placeholder='Digite o Email' sizing='sm' onChange={onChange} {... props}/>
    )
}