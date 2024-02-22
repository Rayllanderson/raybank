'use client'

import { Sidebar as FlowbiteSidebar } from "flowbite-react";
import type { FC, PropsWithChildren } from "react";
import { useSidebarContext } from "../context/SidebarContext";
import { HiViewBoards } from "react-icons/hi";
import { FaBarcode, FaCcMastercard, FaDollarSign, FaMoneyBillTransfer, FaMoneyBillTrendUp, FaPix, FaWallet } from "react-icons/fa6";
import Link from "next/link";

const Sidebar: FC<PropsWithChildren<Record<string, unknown>>> = function ({
  children,
}) {
  const { isOpenOnSmallScreens } = useSidebarContext();
  return (
    <div className={`fixed overflow-auto top-12 h-screen xl:sticky xl:!block xl:translate-x-0 transition-all duration-500 transform'
                ${isOpenOnSmallScreens ? "translate-x-0" : "-translate-x-full"}`}>
      <FlowbiteSidebar className="z-50">{children}</FlowbiteSidebar>
    </div>
  );
};

export default function SideBar() {
  return (
    <Sidebar>
      <FlowbiteSidebar.Items>
        <ul className="mt-4 space-y-2 border-t border-gray-200 pt-4 first:mt-0 first:border-t-0 first:pt-0 dark:border-gray-700">
          <CustomSideBarItem icon={FaWallet} href="/accounts" title='Conta' />

          <CustomSideBarItem icon={FaCcMastercard} href="/cards" title='Cartão de crédito' />

          <CustomSideBarItem icon={FaPix} href="/pix" title='Pix' />

          <CustomSideBarItem icon={FaBarcode} href="/boletos" title='Boleto' />

          <CustomSideBarItem icon={FaMoneyBillTransfer} href="/transfer" title='Transferir' />
          
          <CustomSideBarItem icon={FaDollarSign} href="/payments" title='Pagar' />

          <CustomSideBarItem icon={FaMoneyBillTrendUp} href="/deposits" title='Depositar' />

        </ul>

        <FlowbiteSidebar.ItemGroup>
          <FlowbiteSidebar.Item href="#" icon={HiViewBoards}>
            Help
          </FlowbiteSidebar.Item>
        </FlowbiteSidebar.ItemGroup>
      </FlowbiteSidebar.Items>
    </Sidebar>
  )
}

function CustomSideBarItem({href, icon: Icon, title}: {href: string, icon: any, title: string}) {
  return (
    <li>
      <Link href={href} className="flex items-center justify-center rounded-lg p-2 text-base font-normal text-gray-900 hover:bg-gray-100 dark:text-white dark:hover:bg-gray-700">
        <Icon className="h-6 w-6 flex-shrink-0 text-gray-600 transition duration-75 group-hover:text-gray-900 dark:text-gray-300 dark:group-hover:text-white" />
        <span className="px-3 flex-1 whitespace-nowrap">{title}</span>
      </Link>
    </li>
  )
}