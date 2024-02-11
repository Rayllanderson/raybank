import { InstallmentPlan } from "@/types/InstallmentPlan";
import { get } from "./ApiRequest";
import { snakeToCamel } from "@/utils/StringUtils";
import { ApiErrorException } from "@/types/Error";
import { getToken } from "@/app/api/auth/[...nextauth]/options";

export async function findPlanById(id:string): Promise<InstallmentPlan | null> {
    try {
        const response = await get(`/api/v1/internal/installment-plans/${id}`, await getToken());
        return snakeToCamel(response)
    }catch (err) {
        if (err instanceof ApiErrorException) {
            if (err.httpStatus === 404) {
                return null
            }
        }
        throw err;
    }
}