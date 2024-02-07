"use client"
import ErrorToast from '@/components/ErrorToast'
import { ApiError, ApiErrorException } from '@/types/Error'
import React from 'react'
import { WithoutCreditCard } from './WithoutCreditCard'

export default function error({ error }: { error: ApiErrorException }) {
    return (
        <WithoutCreditCard />
    )
}
