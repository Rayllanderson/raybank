import { FaBagShopping, FaBasketShopping, FaCartShopping, FaCircleArrowDown, FaCircleArrowUp, FaCreditCard, FaMoneyBillTrendUp } from 'react-icons/fa6';
import { formatDate } from '../utils/DateFormatter';
import { isIncomming } from '../utils/StatementUtil';
import { Statement } from '../types/Statement';
import { IconType } from 'react-icons';
import { capitalizeFirstLetter } from '@/utils/StringUtils';

type Props = {
    statement: Statement,
    type: 'account' | 'card',
}

export default function StatementHeader({ statement, type }: Props) {
    return <div className='header flex justify-between items-center '>

        <div className='flex space-x-2 items-center lg:text-lg'>

            {
                type === 'account' ?
                    <AccountHeader statement={statement} /> :
                    <CardHeader statement={statement} />
            }

            <div className='title font-semibold '>
                {statement.title == null ? capitalizeFirstLetter(statement.description) : capitalizeFirstLetter(statement.title)}
            </div>
        </div>

        <div className='flex justify-end'>
            <div className='data'>
                {formatDate(statement.moment)}
            </div>
        </div>
    </div>;
}

interface HeaderProps {
    statement: Statement
}

function AccountHeader({ statement }: HeaderProps) {
    console.log(statement);
    return (
        <>
            {isIncomming(statement.financialMovement?.toString()) ?
                <FaCircleArrowDown className='text-c-green-1 w-5 h-5' />
                :
                <FaCircleArrowUp className='text-red-500 w-5 h-5' />
            }
        </>)
}

function CardHeader({ statement }: HeaderProps) {
    const icons: IconType[] = [FaCartShopping, FaBagShopping, FaBasketShopping]
    const RandomIcon = icons[Math.floor(Math.random() * icons.length)];

    return (
        <>
            {isIncomming(statement.financialMovement?.toString()) ?
                <FaCreditCard className='text-c-green-1 w-5 h-5' />
                :
                <RandomIcon className='dark:text-gray-200 text-gray-800 w-5 h-5' />
            }
        </>)
}
