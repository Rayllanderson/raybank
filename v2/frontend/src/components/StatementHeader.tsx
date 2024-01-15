import { FaBagShopping, FaBasketShopping, FaCartShopping, FaCircleArrowDown, FaCircleArrowUp, FaCreditCard, FaMoneyBillTrendUp } from 'react-icons/fa6';
import { formatDate } from '../utils/DateFormatter';
import { isIncomming } from '../utils/StatementUtil';
import { Statement } from '../types/Statement';
import { IconType } from 'react-icons';

type Props = {
    statement: Statement,
    type: 'account' | 'card',
}

function capitalizeFirstLetter(inputString: string): string {
    return inputString.charAt(0).toUpperCase() + inputString.slice(1);
  }

export default function StatementHeader({ statement, type }: Props) {
    return <div className='header flex justify-between items-center '>

        <div className='flex space-x-2 items-center text-lg'>
            
            {
                type === 'account' ?
                <AccountHeader statement={statement}/> :
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

function AccountHeader({statement}: HeaderProps) {
    return (
        <>
            {isIncomming(statement.financialMovement) ?
                <FaCircleArrowDown className='text-c-green-1 w-6 h-6' />
                :
                <FaCircleArrowUp className='text-red-500 w-6 h-6' />
            }
        </>)
}

function CardHeader({statement}: HeaderProps) {
    const icons: IconType[] = [FaCartShopping, FaBagShopping, FaBasketShopping ]
    const RandomIcon = icons[Math.floor(Math.random() * icons.length)];
    
    return (
        <>
            {isIncomming(statement.financialMovement) ?
                <FaCreditCard  className='text-c-green-1 w-6 h-6' />
                :
                <RandomIcon className='dark:text-gray-200 text-gray-800 w-6 h-6' />
            }
        </>)
}
