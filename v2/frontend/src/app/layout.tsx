import type { Metadata } from 'next'
import { Nunito } from 'next/font/google'
import './globals.css'
import { Flowbite, ThemeModeScript } from 'flowbite-react'
import { twMerge } from "tailwind-merge";
import { flowbiteTheme } from './theme';
import { Providers } from '../providers/providers';
import { Toaster } from 'react-hot-toast';

const inter = Nunito({ 
  weight: "variable",
  subsets: ['latin'],  
  display: "block",
  variable: "--font-nunito"
})

export const metadata: Metadata = {
  title: 'RayBank',
}

export default function RootLayout({children,}: {children: React.ReactNode}) {
  return (
    <html lang="en">
      <head>
        <ThemeModeScript />
      </head>
      <body className={twMerge("bg-gray-50 scrollbar-hide dark:bg-black-1 dark:text-white", inter.variable, "font-sans")}>
        <Providers>
          <Flowbite theme={{ theme: flowbiteTheme }}>
            <Toaster position="top-right"/>
            {children}
          </Flowbite>
        </Providers>
      </body>

    </html>
  )
}
