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
  
      setInputValue(formatter(numericValue));
      onValueChange(numericValue);
    };
    return (
      <input
        {... props}
        type="text"
        value={inputValue}
        onChange={handleInputChange}
        placeholder="R$ 0,00"
      />
    );
  };