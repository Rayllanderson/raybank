import { MoneyFormatter } from '@/utils/MoneyFormatter';
import React from 'react';
import { titlerize } from '@/utils/StringUtils';
import { formatLongDate } from '@/utils/DateFormatter';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { InstallmentPlan } from '@/types/InstallmentPlan';
import { InstallmentCardHeader } from './InstallmentCardHeader';
import { InstallmentCardFooter } from './InstallmentCardFooter';

export function InstallmentCard({ installment }: { installment: InstallmentPlan; }) {
    return <Container>
        <Card>
            <InstallmentCardHeader />
            
            <div className='flex flex-col justify-center text-start space-y-1'>
                <h1 className=' font-semibold text-primary-2 text-2xl'>{titlerize(installment.description)}</h1>
                <h1 className='font-semibold text-2xl'>{MoneyFormatter.format(installment.total)}</h1>
                <p className='text-gray-500 dark:text-gray-300'>
                    {formatLongDate(installment.createdAt)}
                </p>
            </div>
            
            <InstallmentCardFooter installment={installment} />
        </Card>
    </Container>;
}
