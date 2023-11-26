'use client'

import { Sidebar as FlowbiteSidebar } from "flowbite-react";
import type { FC, PropsWithChildren } from "react";
import { useSidebarContext } from "../context/SidebarContext";
import { HiArrowSmRight, HiChartPie, HiInbox, HiShoppingBag, HiTable, HiUser, HiViewBoards } from "react-icons/hi";

const Sidebar: FC<PropsWithChildren<Record<string, unknown>>> = function ({
    children,
}) {
    const { isOpenOnSmallScreens } = useSidebarContext();
    return (
        <div className={`fixed overflow-auto top-12 h-screen z-10 xl:sticky xl:!block xl:translate-x-0 transition-all duration-500 transform'
                ${isOpenOnSmallScreens ? "translate-x-0" : "-translate-x-full"}`}>
            <FlowbiteSidebar>{children}</FlowbiteSidebar>
        </div>
    );
};

export default function SideBar() {
  return (
    <Sidebar>
      <FlowbiteSidebar.Items>
        <FlowbiteSidebar.ItemGroup>
          <FlowbiteSidebar.Item href="#" icon={HiChartPie}>
            Dashboard
          </FlowbiteSidebar.Item>
          <FlowbiteSidebar.Item href="#" icon={HiViewBoards}>
            Kanban
          </FlowbiteSidebar.Item>
          <FlowbiteSidebar.Item href="#" icon={HiInbox}>
            Inbox
          </FlowbiteSidebar.Item>
          <FlowbiteSidebar.Item href="#" icon={HiUser}>
            Users
          </FlowbiteSidebar.Item>
          <FlowbiteSidebar.Item href="#" icon={HiShoppingBag}>
            Products
          </FlowbiteSidebar.Item>
          <FlowbiteSidebar.Item href="#" icon={HiArrowSmRight}>
            Sign In
          </FlowbiteSidebar.Item>
          <FlowbiteSidebar.Item href="#" icon={HiTable}>
            Sign Up
          </FlowbiteSidebar.Item>
        </FlowbiteSidebar.ItemGroup>
        <FlowbiteSidebar.ItemGroup>
          <FlowbiteSidebar.Item href="#" icon={HiChartPie}>
            Upgrade to Pro
          </FlowbiteSidebar.Item>
          <FlowbiteSidebar.Item href="#" icon={HiViewBoards}>
            Documentation
          </FlowbiteSidebar.Item>
          <FlowbiteSidebar.Item href="#" icon={HiViewBoards}>
            Help
          </FlowbiteSidebar.Item>
        </FlowbiteSidebar.ItemGroup>
      </FlowbiteSidebar.Items>
    </Sidebar>
  )
}