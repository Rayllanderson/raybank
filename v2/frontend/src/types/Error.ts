export class ApiErrorException extends Error {
    apiError: ApiError;

    constructor(message: string = "", error: any) {
        console.log(error)
        super(error.message);
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
