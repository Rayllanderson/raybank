import TextHeaderForm from '@/components/TextHeaderForm';
import { Card } from '@/components/cards/Card';
import { CurrencyInput } from '@/components/inputs/InputMoney';
import { Button } from 'flowbite-react';
import React from 'react'
import { FaArrowRight } from 'react-icons/fa6';
import TextHeaderFormLoading from './TextHeaderFormLoading';

interface CurrencyFormProps {
    title: any;
    subtitle?: any;
}

const CurrencyFormLoading: React.FC<CurrencyFormProps> = ({
    title,
    subtitle,
}) => {
    return (
        <Card>
            <div className="mt-2 flex flex-col gap-3">
                <TextHeaderFormLoading title={title} subtitle={subtitle} />
                <CurrencyInput value={0} disabled />
                <div className='flex justify-end'>
                    <NextButton isDisabled={true} />
                </div>
            </div>
        </Card>
    );
};

export default CurrencyFormLoading;


const NextButton = ({ isDisabled }: { isDisabled: boolean }) => (
    <Button color='primary' disabled={isDisabled}>
        <FaArrowRight className='w-6 h-6' />
    </Button>
);