import { InstallmentCard } from '../../../components/cards/InstallmentPlanCard'
import { findPlanById } from '@/services/PlanService'
import { notFound } from 'next/navigation'
import { InstallmentPlan } from '@/types/InstallmentPlan'

export default async function page({ params }: { params: { id: string } }) {
    const installment: InstallmentPlan | null = await findPlanById(params.id)

    if(!installment) {
        notFound()
    }

    return (
        <InstallmentCard installmentPlan={installment} />
    )

   
}
