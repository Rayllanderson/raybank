import jwt from 'jsonwebtoken';

export function decodeToken(token: string): any {
    try {
        const decodedToken = jwt.decode(token);
        return decodedToken;
    } catch (error) {
        console.error("Erro ao decodificar o token:", error);
        return null;
    }
}

export function getAccountIdFromToken(token: string): string {
    if (!token) {
        throw token
    }
    return decodeToken(token).sub;
}