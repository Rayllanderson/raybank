'use client'
import React from 'react'
import BankAccountCard from '../components/cards/BankAccountCard'
import InputWithIcon from '../components/inputs/InputWithIcon'
import { FaSearch } from 'react-icons/fa'
import { FaCircleArrowDown, FaCircleArrowUp } from 'react-icons/fa6'
import { statements } from './mock'
import { formatDate } from '../utils/DateFormatter'
import { MoneyFormatter } from '../utils/MoneyFormatter'
import { isIncomming } from '../utils/StatementUtil'


export default function page() {
    return (
        <div className="cards flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col">
            <BankAccountCard withLinkHeader={false} />

            <div className="mt-8 p-1">
                <h1 className="text-2xl font-semibold font-mono">Histórico</h1>

                <div className='mt-2'>
                    <InputWithIcon icon={FaSearch} type="text" placeholder='Buscar' />
                </div>

                <div className='mt-5'>
                    <div className='space-y-2'>
                        {
                            statements.map(statement => {
                                return (
                                    <div key={statement.id} >

                                        <div className='flex justify-between items-center' >

                                            <div className='flex space-x-2 items-center'>
                                                <div className=''>
                                                    {isIncomming(statement.financialMovement) ?
                                                        <FaCircleArrowUp className='text-green-600 w-6 h-6' />
                                                        :
                                                        <FaCircleArrowDown className='text-red-600 w-6 h-6' />
                                                    }
                                                </div>

                                                <div className='title font-semibold text-xl '>
                                                    {statement.title}
                                                </div>
                                            </div>
                                            <div className='flex  justify-end'>
                                                <div className='data  font-semibold'>
                                                    {formatDate(statement.moment)}
                                                </div>
                                            </div>
                                        </div>

                                        <div className='body p-1 ml-7'>
                                            <div>
                                                <div className='description text-start'>
                                                    <p className='text-lg'>{statement.description}</p>
                                                </div>
                                            </div>

                                            <div>
                                                <div className='description text-start'>
                                                    <p className='text-md'>{MoneyFormatter.format(statement.amount)}</p>
                                                </div>
                                            </div>

                                            <div>
                                                <div className='description text-start'>
                                                    <p className='text-md'>{statement.method}</p>
                                                </div>
                                            </div>

                                        </div>


                                        <hr className='mt-1 border-separate border-gray-300 dark:border-gray-800'></hr>
                                    </div>
                                )
                            })

                        }

                    </div>
                </div>
            </div>
        </div>
    )
}
