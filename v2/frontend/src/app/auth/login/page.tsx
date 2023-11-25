import { BuildingLibraryIcon, UserCircleIcon } from '@heroicons/react/20/solid'
import Link from 'next/link'
import React from 'react'

export default function page() {
    return (
        <div className="flex w-screen h-screen items-center justify-center">
            <form className="flex flex-col max-w-md bg-[#9e9e9e] p-8 rounded ">
                <div className="flex flex-col ">
                    <div className="quadrado-do-icon p-3 rounded-sm flex justify-center">
                        <BuildingLibraryIcon className='w-10' />
                    </div>
                    <div className="quadrado-dos-inputs relative mt-2 rounded-md shadow-sm">
                        <div className="pointer-events-none absolute inset-y-0 left-0 flex items-center pl-3">
                            <span className="text-gray-500 sm:text-sm">
                                <UserCircleIcon className='w-4 mr-3'/>
                            </span>
                        </div>
                        <input
                            type="text"
                            name="price"
                            id="price"
                            className="block w-full rounded-md border-0 py-1.5 pl-7 pr-20 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                            placeholder="0.00"
                        />
                    </div>


                </div>
            </form>
        </div>
    )
}
