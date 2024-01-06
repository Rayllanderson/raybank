import React from 'react'
import { Statement } from '../types/Statement'
import StatementHeader from './StatementHeader'
import { StatementAccountBody } from './StatementAccountBody'
import { StatementCreditCardBody } from './StatementCreditCardBody'

export type StatementProps = {
    statement: Statement,
    type: 'account' | 'card'
}

export default function StatamentCard({ statement, type }: StatementProps) {
    return (
        <div className="p-1">
            <StatementHeader statement={statement}  type={type} />
            {
                type == 'account' ?
                <StatementAccountBody statement={statement}/> :
                <StatementCreditCardBody statement={statement} />
            }
            
            <hr className='mt-1 border-separate border-gray-300 dark:border-gray-800' />
        </div>
    )
}
