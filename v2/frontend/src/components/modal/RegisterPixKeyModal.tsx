'use client'
import React, { useEffect, useState } from 'react';
import { Button, Label, Modal, ModalBody, ModalFooter, ModalHeader, Select } from 'flowbite-react';
import { pixTypeTranslationsStrings } from '@/utils/PixKeyUtil';
import { RegisterCpfInput, RegisterEmailInput, RegisterPhoneInput } from '../inputs/RegisterPixInput';
import { RegexPattern } from '@/utils/PixRegexPattern';
import Separator from '../Separator';


export function RegisterPixKeyModal({ show, setOpenModal }: { show: boolean; setOpenModal: (v: boolean) => void; }) {
    const [keyType, setKeyType] = useState(Object.keys(pixTypeTranslationsStrings)[0])
    const [keyValue, setKeyValue] = useState('')
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    useEffect(() => {
        setIsButtonDisabled(true)
        setKeyValue('')
        if (keyType === 'RANDOM') {
            setIsButtonDisabled(false)
        }
    }, [keyType])

    function handleChange(event: any) {
        const value = event.target.value || null;
        setKeyType(value)
    }

    function handleClick() {
        console.log(keyValue)
        console.log(keyType)
    }

    function handleInputChange(event: any) {
        const value = event.target.value || null;

        if (keyType === 'PHONE') {
            setKeyValue(value?.replace(/[^\d]/g, ''))
            setIsButtonDisabled(!(RegexPattern.PHONE_REGEX.test(value)))
        }

        if (keyType === 'EMAIL') {
            setKeyValue(value)
            setIsButtonDisabled(!(RegexPattern.EMAIL_REGEX.test(value)))
        }

        if (keyType === 'CPF') {
            setKeyValue(value?.replace(/[^\d]/g, ''))
            setIsButtonDisabled(!(RegexPattern.CPF_REGEX.test(value)))
        }

    }

    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size='md'>
            <ModalHeader>Registrar Chave Pix</ModalHeader>
            <ModalBody className='flex flex-col justify-start'>
                <div className='w-full space-y-2'>
                    <div>
                        <Label className='text-base'>Tipo de chave que quer registrar?</Label>
                        <Select required color='default' onChange={handleChange} defaultValue={keyType} value={keyType}>
                            {Object.keys(pixTypeTranslationsStrings).map((pixType) => {
                                return <option key={pixType} value={pixType} >
                                    {pixTypeTranslationsStrings[pixType]}
                                </option>
                            })}
                        </Select>
                    </div>

                    <div>
                        {keyType !== 'RANDOM' && <Label className='text-base'>Digite a chave</Label>}
                        {keyType === 'PHONE' && <RegisterPhoneInput onChange={handleInputChange} value={keyValue} />}
                        {keyType === 'CPF' && <RegisterCpfInput onChange={handleInputChange} value={keyValue} />}
                        {keyType === 'EMAIL' && <RegisterEmailInput onChange={handleInputChange} value={keyValue} />}
                    </div>
                </div>

                <Separator />

                <div className='flex justify-end space-x-2 mt-2'>
                    <Button color="primary" onClick={handleClick} disabled={isButtonDisabled}>Registrar</Button>
                    <Button color='light' onClick={() => setOpenModal(false)}>Cancelar</Button>
                </div>
            </ModalBody>
        </Modal>
    );
}

