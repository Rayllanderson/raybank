import { CustomFlowbiteTheme } from "flowbite-react/lib/esm/components/Flowbite/FlowbiteTheme";

export const flowbiteTheme: CustomFlowbiteTheme = {

    card: {
        root: {
            base: "flex rounded-lg border border-gray-200 bg-white shadow-md dark:border-black-3 dark:bg-black-2",
            children: "flex h-full flex-col justify-center gap-4 p-5 sm:p-6 md:p-6 lg:pd-6"
        }
    },

    button: {
        gradient: {
            "purple": "bg-size-200 text-white bg-gradient-to-r from-purple-500 via-purple-600 to-purple-700 focus:ring-4 focus:ring-purple-300 dark:focus:ring-purple-800 bg-pos-0 hover:bg-pos-100 transition-all",
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
    }
};