//todo:: deletar futuramente
import { FinancialMovementType, StatementType, Statements } from "../types/Statement";

const today = new Date();
const yesterday = new Date();
yesterday.setDate(today.getDate() - 1);

export const statements: Statements = [
    {
        id: "d06050b5-1364-47b6-ac7e-a3b652f3288a",
        moment: today.toString(),
        amount: 780.00,
        description: "SEI LA alguem ai mandou e se for um texto grande demais what will happen?",
        title: "Depósito Recebido",
        type: StatementType.DEPOSIT,
        method: "ACCOUNT",
        financialMovement: FinancialMovementType.CREDIT,
        transactionId: "aac64110-59cb-4b05-beec-753364efa550",
        debit: {
            id: "1ec5f685-aea2-441a-a241-8090e3ddde3d",
            name: "Rayllanderson",
            origin: "ACCOUNT"
        },
        credit: {
            id: "1ec5f685-aea2-441a-a241-8090e3ddde3d",
            name: "Rayllanderson",
            destination: "ACCOUNT"
        }
    },
    {
        id: "d16050b5-1364-47b6-ac7e-a3b652f3288a",
        moment: yesterday.toString(),
        amount: 2310.120,
        description: "SEI LA",
        title: "Depósito Recebido",
        type: StatementType.DEPOSIT,
        method: "ACCOUNT",
        financialMovement: FinancialMovementType.CREDIT,
        transactionId: "aac64110-59cb-4b05-beec-753364efa550",
        debit: {
            id: "1ec5f685-aea2-441a-a241-8090e3ddde3d",
            name: "Rayllanderson",
            origin: "ACCOUNT"
        },
        credit: {
            id: "1ec5f685-aea2-441a-a241-8090e3ddde3d",
            name: "Rayllanderson",
            destination: "ACCOUNT"
        }
    },
    {
        id: "da6050b5-1364-47b6-ac7e-a3b652f3288a",
        moment: "2023-11-11T14:01:06.657712",
        title: "Depósito Recebido",
        amount: 22.00,
        description: "Transferencia via boleto",
        type: StatementType.DEPOSIT,
        method: "ACCOUNT",
        financialMovement: FinancialMovementType.CREDIT,
        transactionId: "aac64110-59cb-4b05-beec-753364efa550",
        debit: {
            id: "1ec5f685-aea2-441a-a241-8090e3ddde3d",
            name: "Rayllanderson",
            origin: "ACCOUNT"
        },
        credit: {
            id: "1ec5f685-aea2-441a-a241-8090e3ddde3d",
            name: "Rayllanderson",
            destination: "ACCOUNT"
        }
    },
    {
        id: "3bf6cdd1-259e-4ecd-91f6-f4ad8c823eba",
        moment: "2023-10-27T20:21:04.759255",
        title: "Compra no Débito",
        amount: 103.00,
        description: "aliabba",
        type: StatementType.PAYMENT,
        method: "DEBIT_CARD",
        financialMovement: FinancialMovementType.DEBIT,
        transactionId: "8b9b1efc-c37f-404b-891b-8630f1fea775",
        debit: {
            id: "0987a631-3948-41f5-8ac1-93c927baded9",
            name: "Rayllanderson",
            origin: "ACCOUNT"
        },
        credit: {
            id: "bbc0a4fb-6ec6-49e7-a428-91197a1e2f0a",
            name: "RayExpress LTDA",
            destination: "ACCOUNT"
        }
    },
    {
        id: "1bf6cdd1-259e-4ecd-91f6-f47d8c823eba",
        moment: "2023-10-27T20:21:04.759255",
        amount: 10.00,
        title: "Transferencia enviada",
        description: "yo!",
        type: StatementType.TRANSFER,
        method: "PIX",
        financialMovement: FinancialMovementType.DEBIT,
        transactionId: "8b9b1efc-c37f-404b-891b-8630f1fea775",
        debit: {
            id: "0987a631-3948-41f5-8ac1-93c927baded9",
            name: "Rayllanderson",
            origin: "ACCOUNT"
        },
        credit: {
            id: "E1397129020231027B71FCB37D48042B",
            name: "RayExpress LTDA",
            destination: "ACCOUNT"
        }
    },
    {
        id: "27cc4bbb-11cb-4e71-87d6-04a18ce6b586",
        moment: "2023-10-27T14:28:45.704756",
        amount: 5643.00,
        description: "nao quero mais",
        title: "Reembolso recebido",
        type: StatementType.RETURN,
        method: "PIX",
        financialMovement: FinancialMovementType.CREDIT,
        transactionId: "499d7228-dbfc-4eac-8065-e5c562a21208",
        debit: {
            id: "E1397129020231027A7307B5BB947476",
            name: "Rayllanderson",
            origin: "PIX"
        },
        credit: {
            id: "0987a631-3948-41f5-8ac1-93c927baded9",
            name: "Rayllanderson",
            destination: "ACCOUNT"
        }
    },
    {
        id: "da6050b5-1364-47b6-ac7e-a3b652f3288a",
        moment: "2023-11-11T14:01:06.657712",
        amount: 20.00,
        description: "SEI LA",
        title: "Depósito Recebido",
        type: StatementType.DEPOSIT,
        method: "ACCOUNT",
        financialMovement: FinancialMovementType.CREDIT,
        transactionId: "aac64110-59cb-4b05-beec-753364efa550",
        debit: {
            id: "1ec5f685-aea2-441a-a241-8090e3ddde3d",
            name: "Rayllanderson",
            origin: "ACCOUNT"
        },
        credit: {
            id: "1ec5f685-aea2-441a-a241-8090e3ddde3d",
            name: "Rayllanderson",
            destination: "ACCOUNT"
        }
    },
    {
        id: "37cc4bbb-11cb-4e71-87d6-04a28ce6b586",
        moment: "2023-10-27T14:28:45.704756",
        amount: 10.00,
        description: "nao quero mais nao",
        title: "Reembolso recebido",
        type: StatementType.RETURN,
        method: "PIX",
        financialMovement: FinancialMovementType.CREDIT,
        transactionId: "499d7228-dbfc-4eac-8065-e5c562a21208",
        debit: {
            id: "E1397129020231027A7307B5BB947476",
            name: "Rayllanderson",
            origin: "PIX"
        },
        credit: {
            id: "0987a631-3948-41f5-8ac1-93c927baded9",
            name: "Rayllanderson",
            destination: "ACCOUNT"
        }
    },
    {
        id: "8bf6cdd1-259e-4ecd-91f6-f47d8c823eba",
        moment: "2023-10-27T20:21:04.759255",
        amount: 10.00,
        description: "aliabba",
        title: "Compra no débito",
        type: StatementType.PAYMENT,
        method: "DEBIT_CARD",
        financialMovement: FinancialMovementType.DEBIT,
        transactionId: "8b9b1efc-c37f-404b-891b-8630f1fea775",
        debit: {
            id: "0987a631-3948-41f5-8ac1-93c927baded9",
            name: "Rayllanderson",
            origin: "ACCOUNT"
        },
        credit: {
            id: "bbc0a4fb-6ec6-49e7-a428-91197a1e2f0a",
            name: "RayExpress LTDA",
            destination: "ACCOUNT"
        }
    },
    {
        id: "1bf6cdd1-2d9e-4ecd-91f6-f47d8c823eba",
        moment: "2023-10-27T20:21:04.759255",
        amount: 10.00,
        description: "yo!",
        title: "Transferênvia enviada",
        type: StatementType.TRANSFER,
        method: "PIX",
        financialMovement: FinancialMovementType.DEBIT,
        transactionId: "8b9b1efc-c37f-404b-891b-8630f1fea775",
        debit: {
            id: "0987a631-3948-41f5-8ac1-93c927baded9",
            name: "Rayllanderson",
            origin: "ACCOUNT"
        },
        credit: {
            id: "E1397129020231027B71FCB37D48042B",
            name: "RayExpress LTDA",
            destination: "ACCOUNT"
        }
    },
    {
        id: "27cc4bbb-11aab-4e71-87d6-04a28ce6b586",
        moment: "2023-10-27T14:28:45.704756",
        amount: 10.00,
        title: "Reembolso recebido",
        description: "nao quero mais",
        type: StatementType.RETURN,
        method: "PIX",
        financialMovement: FinancialMovementType.CREDIT,
        transactionId: "499d7228-dbfc-4eac-8065-e5c562a21208",
        debit: {
            id: "E1397129020231027A7307B5BB947476",
            name: "Rayllanderson",
            origin: "PIX"
        },
        credit: {
            id: "0987a631-3948-41f5-8ac1-93c927baded9",
            name: "Rayllanderson",
            destination: "ACCOUNT"
        }
    },
    {
        id: "1bf6cdd1-2d9e-4ecd-91f6-f47d8c823eba",
        moment: "2023-10-27T20:21:04.759255",
        amount: 10.00,
        description: "yo!",
        title: "Transferênvia recebida",
        type: StatementType.TRANSFER,
        method: "PIX",
        financialMovement: FinancialMovementType.CREDIT,
        transactionId: "8b9b1efc-c37f-404b-891b-8630f1fea775",
        debit: {
            id: "0987a631-3948-41f5-8ac1-93c927baded9",
            name: "Rayllanderson",
            origin: "ACCOUNT"
        },
        credit: {
            id: "E1397129020231027B71FCB37D48042B",
            name: "RayExpress LTDA",
            destination: "ACCOUNT"
        }
    },
]