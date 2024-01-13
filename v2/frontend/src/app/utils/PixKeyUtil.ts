import { PixType } from "../types/Pix";
import { RegexPattern } from "./PixRegexPattern";
import { validate as isUuidValid } from 'uuid';

export const getPixKeyType = (value: string): PixType | null => {
    if (RegexPattern.PHONE_REGEX.test(value)) {
        return PixType.PHONE;
    }
    if (RegexPattern.EMAIL_REGEX.test(value)) {
        return PixType.EMAIL;
    }
    if (RegexPattern.CPF_REGEX.test(value)) {
        return PixType.CPF;
    }
    if (isUuidValid(value)) {
        return PixType.RANDOM;
    }
    return null;
}