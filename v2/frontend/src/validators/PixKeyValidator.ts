import { getPixKeyType } from '../utils/PixKeyUtil';

export function isPixKeyValid(value: string): boolean {
    return getPixKeyType(value) !== null
}