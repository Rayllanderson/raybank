import formatMoney, { MoneyFormatter } from '@/app/utils/MoneyFormatter'
import React from 'react'

interface CardMoneyProps {
  value: string | number;
  size?: string;
  className?: string;
}

export const CardMoney: React.FC<CardMoneyProps> = ({ value, size, className }) => {
  return (
    <p className={`${size === undefined ? 'text-3xl' : size} ${className} font-mono font-bold dark:text-white`}>
      {formatMoney(value)}
    </p>

  )
}
