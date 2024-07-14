import { getAuthAccount, getProfilePicture } from '@/services/AccountService'
import { AccountResponse } from '@/types/Account'
import { Avatar, Dropdown, DropdownItem, DropdownHeader as DropdownHeaderItem, DropdownDivider } from 'flowbite-react'
import Link from 'next/link'
import React from 'react'
import SignOutButton from './SignOutButton'
import AccountDropdownButton from './AccountDropdownButton'

export default async function AvatarDropdown() {
    const userData: AccountResponse | null = await getAuthAccount();
    const profile = await getProfilePicture()

    return (
        <Dropdown
            arrowIcon={false}
            inline

            label={
                <Avatar alt="User settings" img={profile?.thumbnail.preSignedUrl} rounded />
            } className='rounded-md'>
            <DropdownHeaderItem>
                <span className="block text-sm">{userData?.user.name}</span>
                <span className="block text-sm">№ da conta: {userData?.account.number}</span>
            </DropdownHeaderItem>
            <AccountDropdownButton />
            <Link href="/profile"> <DropdownItem>Foto</DropdownItem> </Link>
            <DropdownDivider />
            <SignOutButton />
        </Dropdown>
    )
}
