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
            primary: "text-white enabled:border-0 enabled:active:bg-primary-1-darken bg-primary-2 enabled:hover:bg-primary-1 dark:bg-primary-2 dark:text-white dark:border-primary-2 dark:enabled:hover:bg-primary-1 dark:enabled:active:bg-primary-1-darken ",
            "light": "text-gray-900 bg-gray-300 border border-gray-300 enabled:hover:bg-gray-400  dark:bg-gray-600 dark:text-white dark:border-gray-600 dark:enabled:hover:bg-gray-700 dark:enabled:hover:border-gray-700 dark:focus:ring-gray-700",
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
            base: "bg-white px-2 py-1.5 dark:border-black-3 dark:bg-black-2 sm:px-4",
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
    },
    modal: {
        content: {
            "base": "relative h-full w-full p-4 md:h-auto",
            "inner": "relative rounded-lg bg-white shadow dark:bg-black-1 flex flex-col max-h-[90vh]"
        }
    },

    tabs: {
        "base": "flex flex-col gap-2",
        "tablist": {
            "base": "flex text-center",
            "styles": {
                "underline": "w-full -mb-px border-b border-gray-200 dark:border-gray-700",
            },
            "tabitem": {
                "base": "flex items-center justify-center p-4 rounded-lg text-sm font-medium first:ml-0 disabled:cursor-not-allowed disabled:text-gray-400 disabled:dark:text-gray-500 focus:outline-none",
                "styles": {
                    "underline": {
                        "base": "rounded-t-lg w-full ",
                        "active": {
                            "on": "text-primary-2 rounded-lg border-2 border-primary-2 active",
                            "off": "border-b-2 border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-600 dark:text-gray-400 dark:hover:text-gray-300"
                        }
                    },
                },
                "icon": "mr-2 h-5 w-5"
            }
        },
        "tabpanel": ""
    },
    avatar: {
        root: {
            "size": {
                "xs": "h-6 w-6",
                "sm": "h-8 w-8",
                "md": "h-10 w-10",
                "lg": "h-10 w-10",
                "xl": "h-80 w-80"
            },
            "color": {"primary": "ring-[#830AD1]"}
        }
    }
};  