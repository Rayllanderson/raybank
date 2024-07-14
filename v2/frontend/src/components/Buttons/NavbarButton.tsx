"use client";

import { Bars3Icon, XMarkIcon } from "@heroicons/react/20/solid";
import { useSidebarContext } from "@/context/SidebarContext";

export const NavbarButton = () => {
    const { isOpenOnSmallScreens, setOpenOnSmallScreens } = useSidebarContext();
    return (
        <button
            className="mr-2 cursor-pointer rounded-md p-2 text-gray-600 hover:bg-gray-100 hover:text-gray-900 focus:bg-gray-100 focus:ring-2 focus:ring-gray-100 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white dark:focus:bg-gray-700 dark:focus:ring-gray-700 xl:hidden"
            onClick={() => setOpenOnSmallScreens(!isOpenOnSmallScreens)}>
            {isOpenOnSmallScreens ? (
                <XMarkIcon className="w-6 h-6" />
            ) : (
                <Bars3Icon className="w-6 h-6" />
            )}
        </button>
    )
}