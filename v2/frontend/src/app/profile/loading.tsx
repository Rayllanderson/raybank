import { Card } from '@/components/cards/Card'
import { Avatar } from 'flowbite-react'
import React from 'react'
import ProfileAvatar from './ProfileAvatar'
import { ImSpinner2 } from 'react-icons/im'
import AvatarLoading from '@/components/AvatarLoading'

export default async function loading() {
  return (
    <Card>
      <AvatarLoading size='xl' bordered />
    </Card>
  )
} 
