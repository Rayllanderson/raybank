import React from 'react'

interface Props {
  type?: 'thin' | 'normal'
}

export default function Separator({type = 'normal'}: Props) {
  return (
    <hr className={`mt-1 border-separate ${type === 'normal' ? 'border-gray-300 dark:border-gray-800' : 'border-gray-200 dark:border-gray-900'}`} />
  )
}
