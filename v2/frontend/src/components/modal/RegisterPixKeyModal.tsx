'use client'
import React, { useEffect, useState } from 'react';
import { Button, Label, Modal, ModalBody, ModalFooter, ModalHeader, Select } from 'flowbite-react';
import { pixTypeTranslationsStrings } from '@/utils/PixKeyUtil';
import { RegisterCpfInput, RegisterEmailInput, RegisterPhoneInput } from '../inputs/RegisterPixInput';
import { RegexPattern } from '@/utils/PixRegexPattern';
import Separator from '../Separator';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import InputText from '../inputs/InputText';
import InputNumeric from '../inputs/InputNumeric';
import RegisterPixKeyForm from '../form/RegisterPixKeyForm';



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

    }
 
    return (
        <Modal show={show} onClose={() => setOpenModal(false)} size='md'>
            <ModalHeader>Registrar Chave Pix</ModalHeader>
            <ModalBody className='flex flex-col justify-start'>
                <RegisterPixKeyForm closeModal={() => setOpenModal(false)}/>
            </ModalBody>
        </Modal>
    );
}

