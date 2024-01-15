
const API_URL = process.env.API_URL || 'http://localhost:8080'

export async function get(endpoint: string) {
    try {
        const response = fetch(`${API_URL}/${endpoint}`)
        return await (await response).json()
    } catch (err) {
        console.error(err)
    }
}