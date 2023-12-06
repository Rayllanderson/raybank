import type { Metadata } from 'next'
import { Inter } from 'next/font/google'
import './globals.css'
import { Flowbite, ThemeModeScript } from 'flowbite-react'
import { twMerge } from "tailwind-merge";
import { flowbiteTheme } from './theme';


const inter = Inter({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'RayBank',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <head>
        <ThemeModeScript />
      </head>
      <body className={twMerge("bg-gray-50 scrollbar-hide", inter.className)}>
        <Flowbite theme={{ theme: flowbiteTheme }}>{children}</Flowbite>
      </body>

    </html>
  )
}
