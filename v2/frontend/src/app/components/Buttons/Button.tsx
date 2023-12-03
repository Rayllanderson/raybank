import React from 'react'
import { Button as FlowbiteButton } from 'flowbite-react'
import Link from 'next/link'


interface ButtonProps {
  text: string,
  href: string,
  gradientMonochrome?: string
}


const theme = {
  "gradient": {
    "purple": "bg-size-200 text-white bg-gradient-to-r from-purple-500 via-purple-600 to-purple-700 focus:ring-4 focus:ring-purple-300 dark:focus:ring-purple-800 bg-pos-0 hover:bg-pos-100 bg-gradient-to-r  transition-all",
  },
}


export default function LinkButton({ text, href, gradientMonochrome }: ButtonProps) {
  return (
    <Link href={href}>
      {gradientMonochrome === undefined ?
        <FlowbiteButton>{text}</FlowbiteButton>
        :
        <FlowbiteButton theme={theme} gradientMonochrome={gradientMonochrome} className='bg-size-200 '>{text}</FlowbiteButton>
      }
    </Link>
  )
}
