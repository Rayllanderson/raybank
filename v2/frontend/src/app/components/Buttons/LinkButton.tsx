import React from 'react'
import { Button as FlowbiteButton } from 'flowbite-react'
import Link from 'next/link'

interface ButtonProps {
  href: string,
  gradientMonochrome?: string,
  children?: string
}

export default function LinkButton({ children, href, gradientMonochrome }: ButtonProps) {
  return (
    <Link href={href}>
      {gradientMonochrome === undefined ?
        <FlowbiteButton>{children}</FlowbiteButton>
        :
        <FlowbiteButton gradientMonochrome={gradientMonochrome} className='bg-size-200 w-full'>{children}</FlowbiteButton>
      }
    </Link>
  )
}
