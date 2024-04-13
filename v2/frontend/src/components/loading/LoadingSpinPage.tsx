import React from 'react'
import { ImSpinner2 } from 'react-icons/im'

export default function LoadingSpinPage() {
    return (
        <div className="absolute  inset-0 flex justify-center items-center h-full">
            <ImSpinner2 className="animate-spin h-6 w-6" />
        </div>
    )
}
