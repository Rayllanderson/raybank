import React from 'react'
import { Statement } from '../types/Statement'
import StatementHeader from './StatementHeader'
import { StatementAccountBody } from './StatementAccountBody'
import { StatementCreditCardBody } from './StatementCreditCardBody'
import Separator from './Separator'

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
            
            <Separator />
        </div>
    )
}
