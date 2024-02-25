import React from 'react'

interface Props {
    children: React.ReactNode,
    className?: string
    size?:string
}

export const Container: React.FC<Props> = ({size,className, children}) => {
  return (
    <div className={`flex w-full  flex-col ${className} ${size === undefined ? 'max-w-sm md:max-w-md lg:max-w-lg' : size}`}>
        {children}
    </div>
  )
}
