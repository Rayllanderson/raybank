import { MoneyFormatter } from '@/utils/MoneyFormatter';
import React from 'react';
import { titlerize } from '@/utils/StringUtils';
import { formatLongDate } from '@/utils/DateFormatter';
import { Container } from '@/components/Container';
import { Card } from '@/components/cards/Card';
import { InstallmentPlan } from '@/types/InstallmentPlan';
import { InstallmentCardHeader } from './InstallmentPlanCardHeader';
import { InstallmentCardFooter } from './InstallmentPlanCardFooter';

export function InstallmentCard({ installmentPlan }: { installmentPlan: InstallmentPlan; }) {
    return <Container>
        <Card>
            <InstallmentCardHeader />

            <div className='flex flex-col justify-center text-start space-y-1'>
                <h1 className=' font-semibold text-primary-2 text-2xl'>{titlerize(installmentPlan.description)}</h1>
                <h1 className='font-semibold text-2xl'>{MoneyFormatter.format(installmentPlan.total)}</h1>
                <p className='text-gray-500 dark:text-gray-300'>
                    {formatLongDate(installmentPlan.createdAt)}
                </p>
            </div>

            <InstallmentCardFooter installmentPlan={installmentPlan} />
        </Card>
    </Container>;
}
