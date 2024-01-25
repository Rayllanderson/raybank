'use client'

import { Sidebar as FlowbiteSidebar } from "flowbite-react";
import type { FC, PropsWithChildren } from "react";
import { useSidebarContext } from "../context/SidebarContext";
import { HiViewBoards } from "react-icons/hi";
import { FaBarcode, FaCcMastercard, FaMoneyBillTransfer, FaMoneyBillTrendUp, FaPix, FaWallet } from "react-icons/fa6";
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
        <FlowbiteSidebar.ItemGroup>
          
        <Link href="/accounts">
          <FlowbiteSidebar.Item icon={FaWallet}>
            Conta
          </FlowbiteSidebar.Item>
        </Link>

        <Link href="/cards">
          <FlowbiteSidebar.Item icon={FaCcMastercard}>
            Cartão de crédito
          </FlowbiteSidebar.Item>
          </Link>

          <Link href="/pix">
            <FlowbiteSidebar.Item href="/pix" icon={FaPix}>
              Pix
            </FlowbiteSidebar.Item>
          </Link>

          <Link href="/boletos">
            <FlowbiteSidebar.Item icon={FaBarcode}>
              Boleto
            </FlowbiteSidebar.Item>
          </Link>


          <Link href="/transfer">
          <FlowbiteSidebar.Item icon={FaMoneyBillTransfer}>
            Transferir
          </FlowbiteSidebar.Item>
          </Link>

          <Link href="/deposits">
          <FlowbiteSidebar.Item icon={FaMoneyBillTrendUp}>
            Depositar
          </FlowbiteSidebar.Item>
          </Link>

        </FlowbiteSidebar.ItemGroup>
        <FlowbiteSidebar.ItemGroup>
          <FlowbiteSidebar.Item href="#" icon={HiViewBoards}>
            Help
          </FlowbiteSidebar.Item>
        </FlowbiteSidebar.ItemGroup>
      </FlowbiteSidebar.Items>
    </Sidebar>
  )
}