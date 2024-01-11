import { maxTransactionValue } from '@/app/constants/CurrencyConstans';
import { MoneyFormatter } from '@/app/utils/MoneyFormatter';
import { ChangeEvent, InputHTMLAttributes, useEffect, useState } from 'react';

interface CurrencyInputProps extends InputHTMLAttributes<HTMLInputElement> {
  value: number;
  onValueChange: (value: number) => void;
}

export const CurrencyInput: React.FC<CurrencyInputProps> = ({ value, onValueChange, ...props }) => {
  const formatter = MoneyFormatter.format
  const [inputValue, setInputValue] = useState<string>(formatter(value));

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {

    const rawValue = event.target.value.replace(/[^\d]/g, '');
    const numericValue = parseFloat(rawValue) / 100 || 0;
    const displayedValue = numericValue > maxTransactionValue ? value : numericValue;

    setInputValue(formatter(displayedValue));
    onValueChange(displayedValue);
  };

  return (
    <input
      {...props}
      type="text"
      className='rounded-md w-full h-10 md:h-12 pl-2 lg:h-14 border-0 bg-gray-100 dark:bg-black-3 dark:text-white placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-primary-1 text-md md:text-lg lg:text-xl'
      value={inputValue}
      onChange={handleInputChange}
      placeholder="R$ 0,00"
    />
  );
};