import { getAuthAccount } from '@/services/AccountService'
import { AccountResponse } from '@/types/Account'
import { Dropdown, DropdownItem, DropdownHeader as DropdownHeaderItem, DropdownDivider } from 'flowbite-react'
import Link from 'next/link'
import React from 'react'
import SignOutButton from './SignOutButton'
import AccountDropdownButton from './AccountDropdownButton'
import AvatarPicture from './AvatarPicture'

export default async function AvatarDropdown() {
    const userData: AccountResponse | null = await getAuthAccount();

    return (
        <Dropdown
            arrowIcon={false}
            inline

            label={
                <AvatarPicture />
            } className='rounded-md'>
            <DropdownHeaderItem>
                <span className="block text-sm">{userData?.user.name}</span>
                <span className="block text-sm">№ da conta: {userData?.account.number}</span>
            </DropdownHeaderItem>
            <Link href="/profile"> <DropdownItem>Foto</DropdownItem> </Link>
            <AccountDropdownButton />
            <DropdownDivider />
            <SignOutButton />
        </Dropdown>
    )
}
