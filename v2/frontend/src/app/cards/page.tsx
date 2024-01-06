import { WithCreditCard } from './WithCreditCard';
import { WithoutCreditCard } from './WithoutCreditCard';

const hasCreditCard: boolean = true

export default function page() {
    return (<>
        {hasCreditCard ?
            <WithCreditCard /> :
            <WithoutCreditCard />
        }
    </>
    )
}

