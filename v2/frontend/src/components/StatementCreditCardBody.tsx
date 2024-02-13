import loading from '@/app/cards/loading';
import { Statement } from '../types/Statement';
import { MoneyFormatter } from '../utils/MoneyFormatter';
import LoadingDiv, { loadingDiv } from './LoadingDiv';

type Props = {
    statement: Statement,
}

function hasInstallments(statement: Statement): boolean {
    return !!statement.installmentPlan && statement.installmentPlan.installments > 1
  }

export function StatementCreditCardBody({statement}: Props) {
    return <div className='body p-1 ml-6 text-base dark:text-gray-200 text-gray-800'>
        <div className='flex space-x-1'>
            <p>{MoneyFormatter.format(statement.amount)} </p>
            {hasInstallments(statement) && <p>em {statement.installmentPlan?.installments}x</p> }
        </div>
    </div>;
}

export function StatementCreditCardBodyLoading() {
    return <div className='body p-1 ml-8'>
        <div className='flex w-[25%]'>
            <LoadingDiv className='bg-gray-300 dark:bg-black-3 rounded-md'/>
        </div>
    </div>;
}
