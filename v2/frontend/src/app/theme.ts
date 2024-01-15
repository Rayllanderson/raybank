import { CustomFlowbiteTheme } from "flowbite-react/lib/esm/components/Flowbite/FlowbiteTheme";

export const flowbiteTheme: CustomFlowbiteTheme = {

    card: {
        root: {
            base: "flex rounded-lg border border-gray-200 bg-white shadow-md dark:border-black-3 dark:bg-black-2",
            children: "flex h-full flex-col justify-center gap-4 p-5 sm:p-6 md:p-6 lg:pd-6"
        }
    },

    button: {
        base: "active:scale-[.98] enabled:focus:ring-0 enabled:border-0 dark:enabled:border-0 dark:enabled:focus:ring-0 group flex items-stretch items-center justify-center p-0.5 text-center font-medium relative focus:z-10 focus:outline-none transition duration-100 ease-out hover:ease-in",
        color: {
            primary: "text-white enabled:border-0 enabled:active:bg-primary-1-darken bg-primary-2 enabled:hover:bg-primary-1 dark:bg-primary-2 dark:text-white dark:border-primary-2 dark:enabled:hover:bg-primary-1 dark:enabled:active:bg-primary-1-darken "
        },
        gradient: {
            "purple": "text-white bg-gradient-to-r from-purple-500 via-purple-600 to-purple-700 focus:ring-4 focus:ring-purple-300 dark:focus:ring-purple-800 bg-pos-0 hover:bg-pos-100 bg-size-200",
        },
    },

    sidebar: {
        root: {
            base: "h-full bg-gray-50",
            inner:
                "h-full overflow-y-auto overflow-x-hidden bg-white py-4 px-3 dark:bg-black-2",
        },
    },

    navbar: {
        root: {
            base: "bg-white px-2 py-2.5 dark:border-black-3 dark:bg-black-2 sm:px-4",
        }
    },
    select: {
        field: {
            select: {
                colors: {
                    default: 'bg-gray-100 border-0 text-gray-900 focus:border-primary-1 focus:ring-primary-1 dark:bg-black-3 dark:text-white dark:placeholder-gray-400 dark:focus:border-primary-1 dark:focus:ring-primary-1',
                },
            },
        },
    }
};