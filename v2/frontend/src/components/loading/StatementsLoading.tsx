'use client';
import React from 'react';
import { StatementHeaderLoading } from './StatementHeaderLoading';
import { StatementCreditCardBodyLoading } from './StatementCreditCardBodyLoading';

export function StatementsLoading() {
    return <div>
        {<div className="space-y-2 mt-2">
            {
                [1, 2, 3, 4, 5].map(i => {
                    return <div className="p-4 bg-gray-100 dark:bg-black-2 shadow-lg rounded-md" key={i}>
                        <StatementHeaderLoading />
                        <StatementCreditCardBodyLoading />
                    </div>
                })
            }
        </div>
        }
    </div>;
}
