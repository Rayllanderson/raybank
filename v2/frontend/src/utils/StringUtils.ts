export function titlerize(str: string): string {
    return str.split(/\s+/).map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ');
}

export function capitalizeFirstLetter(inputString: string): string {
    if (!inputString)
        return ''
    return inputString.charAt(0).toUpperCase() + inputString.slice(1);
}

export function snakeToCamel(obj: any): any {
    if (typeof obj !== 'object' || obj === null) {
        return obj;
    }

    if (Array.isArray(obj)) {
        return obj.map(item => snakeToCamel(item));
    }

    const newObj: any = {};
    for (const key in obj) {
        if (Object.prototype.hasOwnProperty.call(obj, key)) {
            const newKey = key.replace(/_([a-z])/g, (_, letter) => letter.toUpperCase());
            newObj[newKey] = snakeToCamel(obj[key]);
        }
    }
    return newObj;
}

export function equalsIgnoreCase(str1: string | null, str2: string | null): boolean {
    if (isNullOrUndefined(str1))
        return false
    if (isNullOrUndefined(str2))
        return false
    return str1!.toLowerCase() === str2!.toLowerCase();
}

export const isNullOrUndefined = (str: string | null): boolean => str === undefined || str === null