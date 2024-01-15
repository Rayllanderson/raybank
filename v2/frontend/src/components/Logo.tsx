import { FaBuildingColumns } from "react-icons/fa6";

export default function Logo() {
    return (
        <div className="flex flex-col space-y-1 mb-8">
            <div className="p-3  rounded-sm flex justify-center">
                <FaBuildingColumns className='w-20 h-20 md:w-24 md:h-24 lg:w-32 lg:h-32 text-primary-1 ' />
            </div>
            <div className='flex justify-center'>
                <h1 className='text-xl text-gray-500 font-semibold font-mono'>RayBank</h1>
            </div>
        </div>
    )
}