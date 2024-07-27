import { DarkThemeToggle, Navbar, NavbarBrand } from "flowbite-react";
import { FC } from "react";
import AvatarDropdown from "./AvatarDropdown";
import Link from "next/link";
import Logo from "./Logo";
import { NavbarButton } from "./Buttons/NavbarButton";


const Header: FC<Record<string, never>> = function () {


    return (
        <header className="sticky top-0 z-20">
            <Navbar fluid className="">
                <NavbarButton/>

                <NavbarBrand as={Link} href="/">
                    <Logo />
                </NavbarBrand>
                <div className="flex md:order-2 space-x-3">
                    <div className="flex justify-center items-center">
                        <DarkThemeToggle />
                    </div>
                    <AvatarDropdown />
                </div>
            </Navbar>
        </header>
    );
};

export default Header;