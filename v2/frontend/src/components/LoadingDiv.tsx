import React from 'react'

export const loadingDiv = <div className='relative overflow-hidden bg-transparent rounded-md w-full p-3 shadow-lg before:absolute before:inset-0 before:-translate-x-full before:bg-gradient-to-r before:from-transparent before:via-white/20 hover:shadow-lg before:animate-[shimmer_1.5s_infinite]'></div>

export const loadingDivDefault = <div className='bg-gray-300 dark:bg-black-3 relative overflow-hidden w-full p-3 shadow-lg before:absolute before:inset-0 before:-translate-x-full before:bg-gradient-to-r before:from-transparent before:via-white/20 hover:shadow-lg before:animate-[shimmer_1.5s_infinite]'></div>


interface Props {
  bg?: string; className?: string, children?:any
}



export default function LoadingDiv({bg, className, children}: Props) {
  return (
    <div className={`${className} ${bg !== undefined ? bg : 'bg-gray-300 dark:bg-black-3'} relative overflow-hidden w-full p-3 shadow-lg before:absolute before:inset-0 before:-translate-x-full before:bg-gradient-to-r before:from-transparent before:via-white/20 hover:shadow-lg before:animate-[shimmer_1.5s_infinite]`}>
      {children}
    </div>
  )
}