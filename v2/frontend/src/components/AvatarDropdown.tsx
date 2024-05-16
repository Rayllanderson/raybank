import { keycloak } from '@/services/KeycloakService'
import { Avatar, Dropdown } from 'flowbite-react'
import { signOut, useSession } from 'next-auth/react'
import React from 'react'

export default function AvatarDropdown() {
    const {data} = useSession()
    const handleLogout = async () => {
        try {
            await keycloak.logout();
            signOut();
        } catch (error) {
            console.error('Erro ao fazer logout:', error);
        }
    };

    const handleClick = () => {
        window.open(process.env.NEXT_PUBLIC_KEYCLOAK_ACCOUNT_INFO!, '_blank');
      };
    return (
        <Dropdown
            arrowIcon={false}
            inline
            
            label={
                <Avatar alt="User settings" img="/avatar.png" rounded />
            } className='rounded-md'>
            <Dropdown.Header>
                <span className="block text-sm">{data?.user.name}</span>
                <span className="block truncate text-sm font-medium">{data?.user.email}</span>
            </Dropdown.Header>
            <Dropdown.Item onClick={handleClick} className="hover:bg-black">Conta</Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item onClick={handleLogout}>Sign out</Dropdown.Item>
        </Dropdown>
    )
}
