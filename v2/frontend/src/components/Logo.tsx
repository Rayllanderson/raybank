import { FaBuildingColumns } from "react-icons/fa6";
import LogoIcon from "./LogoIcon";

export default function Logo() {
    return (
        <div className="flex items-center space-x-1">
            <LogoIcon />
            <h1 className="font-bold text-lg lg:text-xl">Raybank</h1>
        </div>
    )
}