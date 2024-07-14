'use client';

import { Dropdown } from 'flowbite-react';
import React from 'react';
import { keycloak } from '@/services/KeycloakService'
import { signOut } from 'next-auth/react'


const handleLogout = async () => {
        try {
            await keycloak.logout();
            signOut();
        } catch (error) {
            console.error('Erro ao fazer logout:', error);
        }
    };

const SignOutButton: React.FC = () => {
  return (
    <Dropdown.Item onClick={handleLogout}>Sign out</Dropdown.Item>
  );
};

export default SignOutButton;
