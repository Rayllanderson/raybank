import React from 'react'
import { SidebarProvider } from '../context/SidebarContext'
import Header from '../components/Header'
import SideBar from '../components/SideBar'

export default function Layout({ children }: { children: React.ReactNode }) {
    return (
        <SidebarProvider>
            <Header />
            <div className="flex">
                <div className='order-1 fixed z-50'>
                    <SideBar />
                </div>

               <main className="mx-4 mt-5 sm:mt-5 md:mt-8 lg:mt-8 mb-24 w-full h-full order-2 flex justify-center">
                    {children}
                </main>
            </div>
        </SidebarProvider>
    )
}