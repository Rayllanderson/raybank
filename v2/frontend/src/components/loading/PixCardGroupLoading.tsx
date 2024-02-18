import { MiniCardLoadingSkeleton } from '@/components/loading/MiniCardLoading';


export default function PixCardGroupLoading() {
    return (
        <>
            <div className="space-y-5 mt-3">
                <CardGroup>
                    <MiniCardLoadingSkeleton />
                    <MiniCardLoadingSkeleton />
                    <MiniCardLoadingSkeleton />
                </CardGroup>

                <CardGroup>
                    <MiniCardLoadingSkeleton />
                    <MiniCardLoadingSkeleton />
                    <MiniCardLoadingSkeleton />
                </CardGroup>
            </div>
        </>
    )
}

function CardGroup({ children }: { children: any }) {
    return <div className="flex space-x-4 ">
        {children}
    </div>
}
