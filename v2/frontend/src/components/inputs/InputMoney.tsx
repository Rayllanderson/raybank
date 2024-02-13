import { maxTransactionValue } from '@/constants/CurrencyConstans';
import { MoneyFormatter } from '@/utils/MoneyFormatter';
import React, { ChangeEvent, InputHTMLAttributes, LegacyRef, useEffect, useState } from 'react';

interface CurrencyInputProps extends InputHTMLAttributes<HTMLInputElement> {
  value: number;
  ref: LegacyRef<HTMLInputElement>,
  onValueChange?: (value: number) => void;
}

export const CurrencyInput = React.forwardRef<HTMLInputElement, CurrencyInputProps>((props, ref) => {
  const { value, onValueChange, ...restProps } = props;

  const formatter = MoneyFormatter.format
  const [inputValue, setInputValue] = useState<string>(formatter(value));

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {

    const rawValue = event.target.value.replace(/[^\d]/g, '');
    const numericValue = parseFloat(rawValue) / 100 || 0;
    const displayedValue = numericValue > maxTransactionValue ? value : numericValue;

    setInputValue(formatter(displayedValue));
    if (onValueChange)
      onValueChange(displayedValue);
  };

  return (
    <input
      {...restProps}
      type="text"
      className='rounded-md w-full h-12 md:h-14 pl-2 lg:h-16 border-0 bg-gray-100 dark:bg-black-3 dark:text-white placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-primary-1 text-lg md:text-lg lg:text-xl'
      value={inputValue}
      onChange={handleInputChange}
      placeholder="R$ 0,00"
      ref={ref}
      inputMode="numeric" pattern="[0-9]*"
    />
  );
});

CurrencyInput.displayName = 'CurrencyInput';