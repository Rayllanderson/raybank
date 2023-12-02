import formatMoney, { MoneyFormatter } from '@/app/utils/MoneyFormatter'
import React from 'react'

interface CardMoneyProps {
  value: string | number;
  size?: string;
  darkColor?: string;
  className?: string;
}

const defaultTextSize = 'text-2xl md:text-3xl lg:text-3xl'

export const CardMoney: React.FC<CardMoneyProps> = ({ value, size, className, darkColor }) => {
  return (
    <p className={`${size === undefined ? defaultTextSize : size} ${className} font-mono font-bold ${darkColor === undefined ? 'dark:text-white' : darkColor}`}>
      {formatMoney(value)}
    </p>

  )
}
