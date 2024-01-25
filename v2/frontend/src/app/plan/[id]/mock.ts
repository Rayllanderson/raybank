import { InstallmentPlan } from "@/types/InstallmentPlan";

export const installments: InstallmentPlan[] = [
    {
        "id": "bf650238-b665-44a7-a7b2-a161e8a2d6d2",
        "transactionId": "9351e858-0da7-431b-ab61-0b04a67c8a00",
        "originalInvoiceId": "79f14e10-7412-4e14-af3c-c6199b33c8e6",
        "establishmentId": "3d2f2baa-60ad-4fb9-8417-02c353d1f21f",
        "installmentCount": 2,
        "installmentValue": 5.00,
        "total": 10.00,
        "refunded": null,
        "description": "aliabba",
        "createdAt": "2024-01-24T18:44:54.664608",
        "installments": [
            {
                "id": "53a846cd-d191-46f7-97e7-e4731f1ea8a1",
                "description": "aliabba 2/2",
                "value": 5.00,
                "valueToPay": 5.00,
                "status": "OPEN",
                "dueDate": "2024-02-24",
                "invoiceId": "7651dbe6-68f9-464b-a5e2-91b8d3482294"
            },
            {
                "id": "5f4ad21e-7061-40b8-bfcc-6e563982c61d",
                "description": "aliabba 1/2",
                "value": 5.00,
                "valueToPay": 2.50,
                "status": "OPEN",
                "dueDate": "2024-01-24",
                "invoiceId": "79f14e10-7412-4e14-af3c-c6199b33c8e6"
            }
        ]
    },
    {
        "id": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
        "transactionId": "9351e858-0da7-431b-ab61-0b04a67c8a00",
        "originalInvoiceId": "79f14e10-7412-4e14-af3c-c6199b33c8e6",
        "establishmentId": "3d2f2baa-60ad-4fb9-8417-02c353d1f21f",
        "installmentCount": 2,
        "installmentValue": 5.00,
        "total": 10.00,
        "refunded": null,
        "description": "rayexpress",
        "createdAt": "2024-01-24T18:44:54.664608",
        "installments": [
            {
                "id": "53a846cd-d191-46f7-97e7-e4731f1ea8a1",
                "description": "aliabba 2/2",
                "value": 5.00,
                "valueToPay": 5.00,
                "status": "OPEN",
                "dueDate": "2024-02-24",
                "invoiceId": "7651dbe6-68f9-464b-a5e2-91b8d3482294"
            },
            {
                "id": "53a846cd-d191-46f7-97e7-e4731f1ea8a1",
                "description": "aliabba 2/2",
                "value": 5.00,
                "valueToPay": 5.00,
                "status": "PAID",
                "dueDate": "2024-03-24",
                "invoiceId": "7651dbe6-68f9-464b-a5e2-91b8d3482294"
            },
            {
                "id": "53a846cd-d191-46f7-97e7-e4731f1ea8a1",
                "description": "aliabba 2/2",
                "value": 5.00,
                "valueToPay": 5.00,
                "status": "REFUNDED",
                "dueDate": "2024-04-24",
                "invoiceId": "7651dbe6-68f9-464b-a5e2-91b8d3482294"
            },
            {
                "id": "5f4ad21e-7061-40b8-bfcc-6e563982c61d",
                "description": "aliabba 1/2",
                "value": 5.00,
                "valueToPay": 2.50,
                "status": "OPEN",
                "dueDate": "2024-01-24",
                "invoiceId": "79f14e10-7412-4e14-af3c-c6199b33c8e6"
            }
            ,
            {
                "id": "5f4ad21e-7061-40b8-bfcc-6e563982c61d",
                "description": "aliabba 1/2",
                "value": 5.00,
                "valueToPay": 2.50,
                "status": "CANCELED",
                "dueDate": "2024-01-24",
                "invoiceId": "79f14e10-7412-4e14-af3c-c6199b33c8e6"
            }
        ]
    }

]