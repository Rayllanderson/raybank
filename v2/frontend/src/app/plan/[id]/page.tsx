import { FaHouse } from 'react-icons/fa6'
import { installments } from './mock'
import { InstallmentCard } from '../../../components/cards/InstallmentPlanCard'

export default function page({ params }: { params: { id: string } }) {
    const installment = installments.filter(m => m.id === params.id)[0]

    return (
        <InstallmentCard installmentPlan={installment} />
    )

   
}
