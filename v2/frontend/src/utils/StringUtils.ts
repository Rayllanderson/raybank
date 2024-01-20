export function titlerize(str: string): string {
    return str
        .toLowerCase()
        .replace(/\b\w/g, (match) => match.toUpperCase());
}
