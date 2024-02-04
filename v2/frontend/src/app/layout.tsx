import type { Metadata } from 'next'
import { Inter, Nunito } from 'next/font/google'
import './globals.css'
import { Flowbite, ThemeModeScript } from 'flowbite-react'
import { twMerge } from "tailwind-merge";
import { flowbiteTheme } from './theme';
import { Providers } from '../providers/providers';


const inter = Nunito({ subsets: ['latin'] })

export const metadata: Metadata = {
  title: 'RayBank',
}

export default function RootLayout({children,}: {children: React.ReactNode}) {
  return (
    <html lang="en">
      <head>
        <ThemeModeScript />
      </head>
      <body className={twMerge("bg-gray-50 scrollbar-hide dark:bg-black-1 dark:text-white", inter.className)}>
        <Providers>
          <Flowbite theme={{ theme: flowbiteTheme }}>{children}</Flowbite>
        </Providers>
      </body>

    </html>
  )
}
