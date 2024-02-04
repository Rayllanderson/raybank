'use client';
import { SessionProvider } from 'next-auth/react'
import React from 'react'

export default function AuthProvider({children}: {children: any}) {
  return (
    <SessionProvider >
        {children}
    </SessionProvider>
  )
}
