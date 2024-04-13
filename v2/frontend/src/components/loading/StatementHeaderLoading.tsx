import LoadingDiv from '../LoadingDiv';


export function StatementHeaderLoading() {
    const bg = 'bg-gray-300 dark:bg-black-3';

    return <div className='header flex justify-between items-center p1 '>

        <div className='flex space-x-3 justify-center items-center w-full'>

            <div className='w-6 h-6'>
                <LoadingDiv className={`rounded-full ${bg}`} />
            </div>

            <div className='flex-1'>
                <LoadingDiv className={`rounded-md max-w-[40%]  ${bg}`} />
            </div>
        </div>

        <div className='flex justify-end w-[10%]'>
            <div className='w-full'>
                <LoadingDiv className={`rounded-md ${bg}`} />
            </div>
        </div>
    </div>;
}
