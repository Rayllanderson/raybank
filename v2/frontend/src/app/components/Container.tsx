import React from 'react'

interface Props {
    children: React.ReactNode,
    className?: string
}

export const Container: React.FC<Props> = ({className, children}) => {
  return (
    <div className={`flex w-full max-w-sm md:max-w-md lg:max-w-lg flex-col ${className}`}>
        {children}
    </div>
  )
}
