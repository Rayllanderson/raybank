import TextHeaderForm from '@/components/TextHeaderForm';
import { Card } from '@/components/cards/Card';
import { CurrencyInput } from '@/components/inputs/InputMoney';
import { Button } from 'flowbite-react';
import Link from 'next/link';
import React from 'react'
import { FaArrowRight } from 'react-icons/fa6';

interface CurrencyFormProps {
    title: string;
    subtitle?: string;
    amount: number;
    onInputChange: (value: any) => void;
    isButtonDisabled: boolean;
    inputRef?: React.MutableRefObject<HTMLInputElement | null>;
    href: string;
}

const CurrencyForm: React.FC<CurrencyFormProps> = ({
    title,
    subtitle,
    inputRef,
    amount,
    onInputChange,
    href,
    isButtonDisabled,
}) => {
    return (
        <Card>
            <div className="mt-2 flex flex-col gap-3">
                <TextHeaderForm title={title} subtitle={subtitle} />
                <CurrencyInput value={amount} onValueChange={onInputChange} ref={inputRef} />
                <div className='flex justify-end'>
                        {isButtonDisabled ? (
                            <NextButton isDisabled={isButtonDisabled} />
                        ) : (
                            <Link href={href}>
                                <NextButton isDisabled={isButtonDisabled} />
                            </Link>
                        )}
                    </div>
            </div>
        </Card>
    );
};

export default CurrencyForm;


const NextButton = ({ isDisabled }: { isDisabled: boolean }) => (
    <Button color='primary' disabled={isDisabled}>
        <FaArrowRight className='w-6 h-6' />
    </Button>
);