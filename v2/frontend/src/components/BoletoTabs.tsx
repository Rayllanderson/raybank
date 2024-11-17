'use client'

import { equalsIgnoreCase } from '@/utils/StringUtils'
import { Tabs } from 'flowbite-react'
import { useRouter, useSearchParams } from 'next/navigation'
import React from 'react'
import { FaCircleCheck, FaCircleDot, FaCircleXmark, FaClock } from 'react-icons/fa6'
import { Container } from './Container'

const getStatus = (tab: number) => {
    if (tab === 1)
        return 'waiting_payment'
    if (tab === 2)
        return 'paid'
    if (tab === 3)
        return 'expired'
    return 'all'
}

export default function BoletoTabs() {
    const router = useRouter()
    const searchParams = useSearchParams()
    const search = searchParams.get('status')

    const onTableActive = (tab: number) => {
        router.push(`/boletos?status=${getStatus(tab)}`)
    }

    return (
        <Container>

            <Tabs aria-label="Default tabs" style="underline" onActiveTabChange={(tab) => onTableActive(tab)}>
                <Tabs.Item active={search === undefined || search === null || equalsIgnoreCase(search, getStatus(0))} title="Todos" icon={FaCircleDot}>

                </Tabs.Item>
                <Tabs.Item active={equalsIgnoreCase(search, getStatus(1))} title="Pendente" icon={FaClock}>

                </Tabs.Item>
                <Tabs.Item active={equalsIgnoreCase(search, getStatus(2))} title="Pagos" icon={FaCircleCheck}>

                </Tabs.Item>
                <Tabs.Item active={equalsIgnoreCase(search, getStatus(3))} title="Expirados" icon={FaCircleXmark}>

            </Tabs.Item>

            </Tabs>
        </Container>
    )
}
