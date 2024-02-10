export interface Page<T> {
    page: number;
    size: number;
    total: number;
    items: T[];
}
