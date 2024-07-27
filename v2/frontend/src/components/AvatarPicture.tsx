'use client'
import { Avatar } from 'flowbite-react'
import React from 'react'
import { useProfilePicture } from '@/context/ProfilePictureContext'
import AvatarLoading from './AvatarLoading';
import { useSession } from 'next-auth/react';

export default function AvatarPicture() {
    const className = "p-1 rounded-full hover:ring-[#830AD1] transition-all hover:ring-2 "
    const { profilePicture, isThumbCreating } = useProfilePicture();
    const {status} = useSession()

    return (
        status === 'loading' || isThumbCreating ? (
            <AvatarLoading className={className} />
        ) :
            (profilePicture ?
                <Avatar alt="User settings" img={profilePicture?.thumbnail?.preSignedUrl} rounded  className={className} size='lg'/> :
                <Avatar rounded className={className} />
            )
    )
}
