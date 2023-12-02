import React from 'react'
import { SidebarProvider } from '../context/SidebarContext'
import Header from '../components/Header'
import SideBar from '../components/SideBar'

export default function Layout({ children }: { children: React.ReactNode }) {
    return (
        <SidebarProvider>
            <Header />
            <div className="flex dark:bg-gray-900">
                <div className='order-1 fixed'>
                    <SideBar />
                </div>

                <main className="mx-4 mt-4 mb-24 w-full h-full order-2 flex justify-center">
                    {children}
                </main>
            </div>
        </SidebarProvider>
    )
}