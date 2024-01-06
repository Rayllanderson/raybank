import { WithCreditCard } from './WithCreditCard';
import { WithoutCreditCard } from './WithoutCreditCard';

const hasCreditCard: boolean = false

export default function page() {
    return (<>
        {hasCreditCard ?
            <WithCreditCard /> :
            <WithoutCreditCard />
        }
    </>
    )
}

