import { Sidebar as FlowbiteSidebar } from "flowbite-react";
import type { FC, PropsWithChildren } from "react";
import { useSidebarContext } from "../context/SidebarContext";

const Sidebar: FC<PropsWithChildren<Record<string, unknown>>> = function ({
    children,
}) {
    const { isOpenOnSmallScreens } = useSidebarContext();

    return (
        <div className={`fixed overflow-auto top-12 h-screen z-10 lg:sticky lg:!block lg:translate-x-0 transition-all duration-500 transform'
                ${isOpenOnSmallScreens ? "translate-x-0" : "-translate-x-full"}`}>
            <FlowbiteSidebar>{children}</FlowbiteSidebar>
        </div>
    );
};

export default Object.assign(Sidebar, { ...FlowbiteSidebar });