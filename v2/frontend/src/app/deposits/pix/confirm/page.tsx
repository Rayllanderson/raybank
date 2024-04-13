import React from 'react'
import ConfirmPixDepositForm from './ConfirmPixDepositForm'
import { PixService } from '@/services/PixService'


export default async function page() {

    const keys = await PixService.findAllPixKey()

    return <ConfirmPixDepositForm keys={keys}/>
}