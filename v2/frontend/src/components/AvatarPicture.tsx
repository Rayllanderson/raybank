'use client'
import { Avatar } from 'flowbite-react'
import React from 'react'
import { useProfilePicture } from '@/context/ProfilePictureContext'
import AvatarLoading from './AvatarLoading';
import { useSession } from 'next-auth/react';

export default function AvatarPicture() {
    const { profilePicture, isThumbCreating } = useProfilePicture();
    const {status} = useSession()

    console.log(profilePicture)

    console.log(isThumbCreating)
    
    return (
        status === 'loading' || isThumbCreating ? (
            <AvatarLoading />
        ) :
            (profilePicture ?
                <Avatar alt="User settings" img={profilePicture?.thumbnail?.preSignedUrl} rounded /> :
                <Avatar rounded />
            )
    )
}
