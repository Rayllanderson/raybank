import formatMoney, { MoneyFormatter } from '@/app/utils/MoneyFormatter'
import React from 'react'

interface CardMoneyProps {
  value: string | number
}

export default function CardMoney({ value }: CardMoneyProps) {
  return (
    <p className="text-3xl font-mono font-bold dark:text-white"> {formatMoney(value)} </p>
  )
}
