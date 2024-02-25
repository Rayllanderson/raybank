import React from 'react'
import { Statement } from '../types/Statement'
import StatementHeader from './StatementHeader'
import { StatementAccountBody } from './StatementAccountBody'
import { StatementCreditCardBody } from './StatementCreditCardBody'
import Separator from './Separator'

export type StatementProps = {
    statement: Statement,
    type: 'account' | 'card'
    onClick: (value: any) => void
}

export default function StatamentCard({ statement, type,onClick }: StatementProps) {
    
    return (
        <div className="p-3 shadow-sm rounded-md bg-gray-100 dark:bg-black-2 cursor-pointer hover:scale-[1.019] transition-transform" onClick={onClick}>
            <div className="p-1">
                <StatementHeader statement={statement} type={type} />
                {
                    type == 'account' ?
                        <StatementAccountBody statement={statement} /> :
                        <StatementCreditCardBody statement={statement} />
                }
            </div>
        </div>
    )
}
