import { CurrencyInput } from "@/app/components/inputs/InputMoney";
import { useState } from "react";

const MoneyForm: React.FC = () => {
  const [amount, setAmount] = useState<number>(0);

  const handleAmountChange = (value: number) => {
    setAmount(value);
  };

  return (
    <CurrencyInput value={amount} onValueChange={handleAmountChange} className='rounded-md w-full h-10 md:h-12 pl-2 lg:h-14 border-0 bg-gray-100 dark:bg-black-2 dark:text-white placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-primary-1 text-md md:text-lg lg:text-xl' />
  )
};

export default MoneyForm;