import type { Config } from 'tailwindcss'
const defaultTheme = require('tailwindcss/defaultTheme');

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
      fontFamily: {
//         sans: ['var(--font-nunito)'],
//         mono: ['var(--font-nunito)'],
      },
      backgroundSize: {
        'size-200': '200% 200%',
      },
      backgroundPosition: {
        'pos-0': '0% 0%',
        'pos-100': '100% 100%',
      },
      colors: {
        'primary-1': '#7E04B9',
        'primary-2': '#830AD1',
        'primary-3': '#9B3BDA',
        'primary-1-darken': '#6F03A8',
        'c-blue-1': '#279EE0',
        'c-green-1': '#227B4D',
        'black-1': '#0D1117',
        'black-2': '#161B22',
        'black-3': '#1A1F25'
      }
    },
    keyframes: {
      ...defaultTheme.keyframes,
      shimmer: {
        '100%' : {transform: 'translateX(100%)'}
      }
    }
  }
}
export default config