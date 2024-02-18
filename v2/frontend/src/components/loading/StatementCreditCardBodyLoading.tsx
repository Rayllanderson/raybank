import LoadingDiv from '../LoadingDiv';


export function StatementCreditCardBodyLoading() {
    return <div className='body p-1 ml-8'>
        <div className='flex w-[25%]'>
            <LoadingDiv className='bg-gray-300 dark:bg-black-3 rounded-md' />
        </div>
    </div>;
}
