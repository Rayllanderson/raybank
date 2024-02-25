import { formatStatementMethod, hasDescription, isIncomming } from '@/utils/StatementUtil';
import { Statement } from '../types/Statement';
import { MoneyFormatter } from '../utils/MoneyFormatter';

type Props = {
    statement: Statement,
}

export function StatementAccountBody({ statement }: Props) {
    return <div className='body p-1 ml-7 text-base dark:text-gray-200 text-gray-800'>
        <div className='description'>
            {hasDescription(statement) &&
                (isIncomming(statement.financialMovement.toString()) ? statement.debit.name : statement.credit.name)
            }
        </div>

        <div className='description'>
            {MoneyFormatter.format(statement.amount)}
        </div>

        <div className='description'>
            {formatStatementMethod(statement.method)}
        </div>
    </div>;
}
