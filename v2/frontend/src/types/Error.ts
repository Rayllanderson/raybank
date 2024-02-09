export class ApiErrorException extends Error {
    apiError: ApiError;
    httpStatus: number;

    constructor(status: number, error: any) {
        super(error.message);
        this.httpStatus = status;
        this.name = 'ApiErrorException'
        this.apiError = error
    }
}



export interface ApiError {
    title:        string;
    status:       number;
    message:      string;
    raybankError: RaybankError;
    timestamp:    Date;
    path:         string;
    fieldErrors:  FieldError[];
}

export interface FieldError {
    field:   string;
    message: string;
}

export interface RaybankError {
    code:        string;
    description: string;
}
