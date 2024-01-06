import { Statement } from '../types/Statement';
import { MoneyFormatter } from '../utils/MoneyFormatter';

type Props = {
    statement: Statement,
}

function hasInstallments(statement: Statement): boolean {
    return !!statement.installmentPlan && statement.installmentPlan.installments > 1
  }

export function StatementCreditCardBody({statement}: Props) {
    return <div className='body p-1 ml-7 text-base dark:text-gray-200 text-gray-800'>
        <div className='flex space-x-1'>
            <p>{MoneyFormatter.format(statement.amount)} </p>
            {hasInstallments(statement) && <p>em {statement.installmentPlan?.installments}x</p> }
        </div>
    </div>;
}
