import React from 'react'
import { Button as FlowbiteButton } from 'flowbite-react'
import Link from 'next/link'

interface ButtonProps {
  href: string,
  gradientMonochrome?: string,
  children?: string,
  hrefQueryParams?: any
}

export default function LinkButton({ children, href,hrefQueryParams: hrefData, gradientMonochrome }: ButtonProps) {
  return (
    <Link href={{
      pathname: href,
      query: hrefData
    }} >
      {gradientMonochrome === undefined ?
        <FlowbiteButton>{children}</FlowbiteButton>
        :
        <FlowbiteButton gradientMonochrome={gradientMonochrome} className='bg-size-200 w-full'>{children}</FlowbiteButton>
      }
    </Link>
  )
}
