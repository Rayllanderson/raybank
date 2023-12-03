import React from 'react'
import { Button as FlowbiteButton } from 'flowbite-react'
import Link from 'next/link'


interface ButtonProps {
  href: string,
  gradientMonochrome?: string,
  children?: string
}


const theme = {
  "gradient": {
    "purple": "bg-size-200 text-white bg-gradient-to-r from-purple-500 via-purple-600 to-purple-700 focus:ring-4 focus:ring-purple-300 dark:focus:ring-purple-800 bg-pos-0 hover:bg-pos-100 bg-gradient-to-r  transition-all",
  },
}


export default function LinkButton({ children, href, gradientMonochrome }: ButtonProps) {
  return (
    <Link href={href}>
      {gradientMonochrome === undefined ?
        <FlowbiteButton>{children}</FlowbiteButton>
        :
        <FlowbiteButton theme={theme} gradientMonochrome={gradientMonochrome} className='bg-size-200 w-full'>{children}</FlowbiteButton>
      }
    </Link>
  )
}
