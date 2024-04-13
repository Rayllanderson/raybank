import { ApiErrorException } from "@/types/Error"
import toast from "react-hot-toast"

export function handlerApiError(err: unknown, defaultMessage: string = "Ocorreu um erro") {
    if (err instanceof ApiErrorException) {
        if (err.httpStatus === 400 || err.httpStatus === 422) {
            toast.error(err.message)
            return;
        }
    }
    toast.error(defaultMessage)
}