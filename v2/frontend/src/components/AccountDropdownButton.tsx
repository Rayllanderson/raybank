'use client';

import { Dropdown } from 'flowbite-react';
import React from 'react';


const handleClick = () => {
    window.open(process.env.NEXT_PUBLIC_KEYCLOAK_ACCOUNT_INFO!, '_blank');
};

const AccountDropdownButton: React.FC = () => {
    const isKeycloak = process.env.PROVIDER === 'keycloak'
    return (
        isKeycloak &&
        <Dropdown.Item onClick={handleClick}>Sua Conta</Dropdown.Item>
    );
};

export default AccountDropdownButton;
