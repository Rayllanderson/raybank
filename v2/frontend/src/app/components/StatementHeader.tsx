import { FaCircleArrowDown, FaCircleArrowUp } from 'react-icons/fa6';
import { formatDate } from '../utils/DateFormatter';
import { isIncomming } from '../utils/StatementUtil';
import { StatementProps } from './StatamentCard';

export default function StatementHeader({statement}: StatementProps) {
    return <div className='header flex justify-between items-center '>

        <div className='flex space-x-2 items-center text-lg'>
            {isIncomming(statement.financialMovement) ?
                <FaCircleArrowDown className='text-c-green-1 w-6 h-6' />
                :
                <FaCircleArrowUp className='text-red-500 w-6 h-6' />
            }

            <div className='title font-semibold '>
                {statement.title}
            </div>
        </div>

        <div className='flex justify-end'>
            <div className='data'>
                {formatDate(statement.moment)}
            </div>
        </div>
    </div>;
}
