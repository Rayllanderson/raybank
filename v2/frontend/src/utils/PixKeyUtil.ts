import { PixType } from "@/types/Pix";
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

export const pixTypeTranslations: Record<PixType, string> = {
    [PixType.PHONE]: 'Telefone',
    [PixType.EMAIL]: 'E-mail',
    [PixType.RANDOM]: 'Aleatória',
    [PixType.CPF]: 'CPF',
};

export const pixTypeTranslationsStrings: Record<string, string> = {
    ['PHONE']: 'Telefone',
    ['EMAIL']: 'E-mail',
    ['RANDOM']: 'Aleatória',
    ['CPF']: 'CPF',
};

export const pixTypeTranslationForTransfer: Record<PixType, string> = {
    [PixType.PHONE]: 'esse Telefone',
    [PixType.EMAIL]: 'esse E-mail',
    [PixType.RANDOM]: 'essa chave Aleatória',
    [PixType.CPF]: 'esse CPF',
};

export const getPixKeyTypeAsString = (value: string): string | null => {
    const pixKeyType: PixType | null = getPixKeyType(value);
    return pixKeyType !== null ? pixTypeTranslations[pixKeyType] : null;
};

export const getPixKeyTypeAsStringForTransfer = (value: string): string | null => {
    const pixKeyType: PixType | null = getPixKeyType(value);
    return pixKeyType !== null ? pixTypeTranslationForTransfer[pixKeyType] : null;
};
