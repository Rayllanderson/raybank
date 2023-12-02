import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    "./node_modules/flowbite-react/**/*.js",
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  plugins: [
    require('flowbite/plugin'),
    require('tailwind-scrollbar-hide')
  ],
  theme: {
    extend: {
      colors: {
        'primary-1': '#7E04B9',
        'primary-2': '#830AD1',
        'primary-3': '#9B3BDA',
        'c-blue-1': '#279EE0',
        'c-green-1': '#227B4D'
      }
    }
  }
}
export default config
