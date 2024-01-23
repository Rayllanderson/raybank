import { Invoice } from "@/types/Invoice";

export const invoices: Invoice[] = [
    {
      "id": "76ee7278-a85d-4076-8637-a395b2ec8202",
      "dueDate": "2023-12-12",
      "closingDate": "2023-12-05",
      "total": -5.00,
      "status": "OPEN"
    },
    {
      "id": "123e4567-e89b-12d3-a456-426614174001",
      "dueDate": "2023-11-10",
      "closingDate": "2023-11-03",
      "total": 15.50,
      "status": "CLOSED"
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
      "status": "OPEN"
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
      "status": "OVERDUE"
    },
    {
      "id": "abcdef12-3456-7890-ef12-98765432abcd",
      "dueDate": "2023-07-05",
      "closingDate": "2023-06-28",
      "total": 30.75,
      "status": "OPEN"
    }
  ];