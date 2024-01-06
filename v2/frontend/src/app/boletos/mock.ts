import { Boleto } from "../types/Boleto";

export const boletos: Boleto[] = [
    {
        "barCode": "13196172719920592212048014211377552680000000076",
        "value": 76.00,
        "status": "PAID",
        "createdAt": "2024-01-06",
        "expirationDate": "2024-01-06",
        "beneficiary": "ITAU UNIBANCO"
    },
    {
        "barCode": "13196172719920592212048014211377552680000000075",
        "value": 17326.20,
        "status": "PAID",
        "createdAt": "2024-01-06",
        "expirationDate": "2024-01-05",
        "beneficiary": "ITAU UNIBANCO"
    },
    {
        "barCode": "13196172719920592212048014211377552680000000071",
        "value": 426.23,
        "status": "EXPIRED",
        "createdAt": "2023-10-14",
        "expirationDate": "2023-10-17",
        "beneficiary": "NUBANK"
    },
    {
        "barCode": "13196172719920592212048014211377552680000000072",
        "value": 426.23,
        "status": "EXPIRED",
        "createdAt": "2023-10-14",
        "expirationDate": "2023-10-17",
        "beneficiary": "ITAU UNIBANCO"
    }
]