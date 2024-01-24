import { Invoice } from "@/types/Invoice";

export const invoices: Invoice[] = [
    {
      "id": "76ee7278-a85d-4076-8637-a395b2ec8202",
      "dueDate": "2023-12-12",
      "closingDate": "2023-12-05",
      "total": -5.00,
      "status": "OPEN",
      "transactions": [
        {
          "id": "73e9cc88-5bcf-4797-ba6d-35e3c0240bf7",
          "value": 5.00,
          "description": "aliabba 2/2",
          "occuredOn": "2023-12-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "OPEN"
        }
      ]
    },
    {
      "id": "123e4567-e89b-12d3-a456-426614174001",
      "dueDate": "2023-11-10",
      "closingDate": "2023-11-03",
      "total": 15.50,
      "status": "CLOSED",
      "transactions": [
        {
          "id": "73e9cc88-5bcf-4797-ba6d-35e3c0240bf7",
          "value": 5.00,
          "description": "aliabba 2/2",
          "occuredOn": "2023-12-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "OPEN"
        },
        {
          "id": "bf838300-e63f-47eb-8d92-bd082b59946e",
          "value": 8.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:12.093105",
          "type": "REFUND"
        },
        {
          "id": "fb0b115f-f84a-485b-8e6a-cd16a24cbd78",
          "value": 2.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:04.54581",
          "type": "REFUND"
        },
        {
          "id": "04ed3df4-effb-43c0-be5b-dad61bc8b0b7",
          "value": 5.00,
          "description": "aliabba 1/2",
          "occuredOn": "2023-11-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "PAID"
        },
        {
          "id": "bf838300-e63f-47eb-8d92-bd082b59946e",
          "value": 8.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:12.093105",
          "type": "REFUND"
        },
        {
          "id": "fb0b115f-f84a-485b-8e6a-cd16a24cbd78",
          "value": 2.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:04.54581",
          "type": "REFUND"
        },
        {
          "id": "04ed3df4-effb-43c0-be5b-dad61bc8b0b7",
          "value": 5.00,
          "description": "aliabba 1/2",
          "occuredOn": "2023-11-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "PAID"
        },
        {
          "id": "bf838300-e63f-47eb-8d92-bd082b59946e",
          "value": 8.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:12.093105",
          "type": "REFUND"
        },
        {
          "id": "fb0b115f-f84a-485b-8e6a-cd16a24cbd78",
          "value": 2.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:04.54581",
          "type": "REFUND"
        },
        {
          "id": "04ed3df4-effb-43c0-be5b-dad61bc8b0b7",
          "value": 5.00,
          "description": "aliabba 1/2",
          "occuredOn": "2023-11-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "PAID"
        },
        {
          "id": "bf838300-e63f-47eb-8d92-bd082b59946e",
          "value": 8.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:12.093105",
          "type": "REFUND"
        },
        {
          "id": "fb0b115f-f84a-485b-8e6a-cd16a24cbd78",
          "value": 2.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:04.54581",
          "type": "REFUND"
        },
        {
          "id": "04ed3df4-effb-43c0-be5b-dad61bc8b0b7",
          "value": 5.00,
          "description": "aliabba 1/2",
          "occuredOn": "2023-11-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "PAID"
        },
        {
          "id": "bf838300-e63f-47eb-8d92-bd082b59946e",
          "value": 8.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:12.093105",
          "type": "REFUND"
        },
        {
          "id": "fb0b115f-f84a-485b-8e6a-cd16a24cbd78",
          "value": 2.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:04.54581",
          "type": "REFUND"
        },
        {
          "id": "04ed3df4-effb-43c0-be5b-dad61bc8b0b7",
          "value": 5.00,
          "description": "aliabba 1/2",
          "occuredOn": "2023-11-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "PAID"
        },
        {
          "id": "bf838300-e63f-47eb-8d92-bd082b59946e",
          "value": 8.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:12.093105",
          "type": "REFUND"
        },
        {
          "id": "fb0b115f-f84a-485b-8e6a-cd16a24cbd78",
          "value": 2.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:04.54581",
          "type": "REFUND"
        },
        {
          "id": "04ed3df4-effb-43c0-be5b-dad61bc8b0b7",
          "value": 5.00,
          "description": "aliabba 1/2",
          "occuredOn": "2023-11-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "PAID"
        },
        {
          "id": "bf838300-e63f-47eb-8d92-bd082b59946e",
          "value": 8.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:12.093105",
          "type": "REFUND"
        },
        {
          "id": "fb0b115f-f84a-485b-8e6a-cd16a24cbd78",
          "value": 2.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:04.54581",
          "type": "REFUND"
        },
        {
          "id": "04ed3df4-effb-43c0-be5b-dad61bc8b0b7",
          "value": 5.00,
          "description": "aliabba 1/2",
          "occuredOn": "2023-11-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "PAID"
        }
      ]
    },
    {
      "id": "abcdef12-3456-7890-abcd-ef1234567890",
      "dueDate": "2024-01-18",
      "closingDate": "2024-01-11",
      "total": 25.00,
      "status": "NONE"
    },
    {
      "id": "98765432-abcd-ef12-3456-7890abcdef12",
      "dueDate": "2023-10-20",
      "closingDate": "2023-10-13",
      "total": -8.75,
      "status": "PAID",
      "transactions": [
        {
          "id": "73e9cc88-5bcf-4797-ba6d-35e3c0240bf7",
          "value": 5.00,
          "description": "aliabba 2/2",
          "occuredOn": "2023-12-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "OPEN"
        },
        {
          "id": "bf838300-e63f-47eb-8d92-bd082b59946e",
          "value": 8.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:12.093105",
          "type": "REFUND"
        },
        {
          "id": "fb0b115f-f84a-485b-8e6a-cd16a24cbd78",
          "value": 2.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:04.54581",
          "type": "REFUND"
        },
        {
          "id": "04ed3df4-effb-43c0-be5b-dad61bc8b0b7",
          "value": 5.00,
          "description": "aliabba 1/2",
          "occuredOn": "2023-11-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "PAID"
        }
      ]
    },
    {
      "id": "abcdef12-7890-3456-ef12-987654321098",
      "dueDate": "2023-09-15",
      "closingDate": "2023-09-08",
      "total": 50.00,
      "status": "CLOSED"
    },
    {
      "id": "abcdef12-3456-7890-ef12-9876543210ab",
      "dueDate": "2023-08-22",
      "closingDate": "2023-08-15",
      "total": -12.30,
      "status": "OVERDUE",
      "transactions": [
        {
          "id": "73e9cc88-5bcf-4797-ba6d-35e3c0240bf7",
          "value": 5.00,
          "description": "aliabba 2/2",
          "occuredOn": "2023-12-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "OPEN"
        },
        {
          "id": "bf838300-e63f-47eb-8d92-bd082b59946e",
          "value": 8.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:12.093105",
          "type": "REFUND"
        },
        {
          "id": "fb0b115f-f84a-485b-8e6a-cd16a24cbd78",
          "value": 2.00,
          "description": "Estorno de aliabba",
          "occuredOn": "2023-11-05T22:57:04.54581",
          "type": "REFUND"
        },
        {
          "id": "04ed3df4-effb-43c0-be5b-dad61bc8b0b7",
          "value": 5.00,
          "description": "aliabba 1/2",
          "occuredOn": "2023-11-05T22:56:03.145839",
          "type": "INSTALLMENT",
          "planId": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
          "status": "PAID"
        }
      ]
    },
    {
      "id": "abcdef12-3456-7890-ef12-98765432abcd",
      "dueDate": "2023-07-05",
      "closingDate": "2023-06-28",
      "total": 30.75,
      "status": "PAID"
    }
  ];