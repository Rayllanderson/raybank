import { keycloak } from '@/services/KeycloakService'
import { Avatar, Dropdown } from 'flowbite-react'
import { signOut } from 'next-auth/react'
import React from 'react'

export default function AvatarDropdown() {
    const handleLogout = async () => {
        try {
            await keycloak.logout();
            signOut();
        } catch (error) {
            console.error('Erro ao fazer logout:', error);
        }
    };

    return (
        <Dropdown
            arrowIcon={false}
            inline
            
            label={
                <Avatar alt="User settings" img="/avatar.png" rounded />
            } className='rounded-md'>
            <Dropdown.Header>
                <span className="block text-sm">Bonnie Green</span>
                <span className="block truncate text-sm font-medium">name@flowbite.com</span>
            </Dropdown.Header>
            <Dropdown.Item className="hover:bg-black">Dashboard</Dropdown.Item>
            <Dropdown.Item>Settings</Dropdown.Item>
            <Dropdown.Item>Earnings</Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item onClick={handleLogout}>Sign out</Dropdown.Item>
        </Dropdown>
    )
}
