'use client'

import { DarkThemeToggle , Navbar, NavbarLink } from "flowbite-react";
import Image from "next/image";
import { FC } from "react";
import { useSidebarContext } from "../context/SidebarContext";
import AvatarDropdown from "./AvatarDropdown";
import { Bars3Icon, XMarkIcon } from "@heroicons/react/20/solid";
import Link from "next/link";
import LogoIcon from "./LogoIcon";
import Logo from "./Logo";

const Header: FC<Record<string, never>> = function () {
    const { isOpenOnSmallScreens, setOpenOnSmallScreens } = useSidebarContext();

    return (
        <header className="sticky top-0 z-20">
            <Navbar fluid className="">
                <button
                    className="mr-2 cursor-pointer rounded-md p-2 text-gray-600 hover:bg-gray-100 hover:text-gray-900 focus:bg-gray-100 focus:ring-2 focus:ring-gray-100 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white dark:focus:bg-gray-700 dark:focus:ring-gray-700 xl:hidden"
                    onClick={() => setOpenOnSmallScreens(!isOpenOnSmallScreens)}>
                    {isOpenOnSmallScreens ? (
                        <XMarkIcon className="w-6 h-6" />
                    ) : (
                        <Bars3Icon className="w-6 h-6" />
                    )}
                </button>

                <Navbar.Brand as={Link} href="/">
                   <Logo/>
                </Navbar.Brand>
                <div className="flex md:order-2 space-x-3">
                    <DarkThemeToggle />
                    <AvatarDropdown />
                </div>
            </Navbar>
        </header>
    );
};

export default Header;