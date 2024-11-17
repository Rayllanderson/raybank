import CreatePurchaseForm from '@/app/purchase/CreatePurchaseForm'
import { createPurchase } from '@/services/CardService'
import { createPurchaseFormSchema } from '@/types/Card'
import type { NextApiRequest, NextApiResponse } from 'next'
import { NextResponse } from 'next/server'

export async function POST(
    req: Request,
    res: Response
) {
    const parsed = createPurchaseFormSchema.parse(await req.json())
    const id = await createPurchase(parsed)

    return new Response(JSON.stringify(id), { status: 200 });
}