import { validate as isUuidValid } from 'uuid';
import { RegexPattern } from '../utils/PixRegexPattern';
import { getPixKeyType } from '../utils/PixKeyUtil';

export function isPixKeyValid(value: string): boolean {
    // console.log(`is CPF_REGEX: ${RegexPattern.CPF_REGEX.test(value)}`)
    // console.log(`is EMAIL_REGEX: ${RegexPattern.EMAIL_REGEX.test(value)}`)
    // console.log(`is PHONE_REGEX: ${RegexPattern.PHONE_REGEX.test(value)}`)
    // console.log(`is isUuidValid: ${isUuidValid(value)}`)
    return getPixKeyType(value) !== null
}