import React from 'react'
import { Statement } from '../types/Statement'
import StatementHeader from './StatementHeader'
import { StatementBody } from './StatementBody'

export type StatementProps = {
    statement: Statement
}

export default function StatamentCard({ statement }: StatementProps) {
    return (
        <div className="p-1">
            <StatementHeader statement={statement} />
            <StatementBody statement={statement} />
            <hr className='mt-1 border-separate border-gray-300 dark:border-gray-800' />
        </div>
    )
}
