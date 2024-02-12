"use server"

import { CardService } from "@/services/CardService";
import { ChangeCardLimitFormData } from "@/types/Card";
import { revalidatePath } from "next/cache";

export async function changeLimit(data: ChangeCardLimitFormData, token: string) {
    try {
        await CardService.changeLimit(data, token)
        revalidatePath("/cards")
    }catch(e) {
        console.log(e)
        throw e
    } 
}