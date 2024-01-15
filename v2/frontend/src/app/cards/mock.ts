//todo:: deletar futuramente
import { FinancialMovementType, StatementType, Statements } from "../../types/Statement";

const today = new Date();
const yesterday = new Date();
yesterday.setDate(today.getDate() - 1);
const other = new Date(yesterday);
other.setDate(yesterday.getDate() - 1);

export const statements: Statements = [
    {
        id: "2eaf39fa-7f83-4408-87be-c718c2f7dbae",
        moment: today.toString(),
        title: null,
        amount: 10.00,
        description: "aliabba",
        type: StatementType.PAYMENT,
        method: "CREDIT_CARD",
        financialMovement: FinancialMovementType.DEBIT,
        transactionId: "49f2209f-1f3a-40b8-916c-dab712eb4e5e",
        debit: {
            "id": "2c76baf8-1a35-4ef2-bad7-3e7d042362a2",
            "name": "Cartão de Crédito",
            "origin": "CREDIT_CARD"
        },
        credit: {
            "id": "3d2f2baa-60ad-4fb9-8417-02c353d1f21f",
            "name": "RayExpress LTDA",
            "destination": "ESTABLISHMENT_ACCOUNT"
        },
        installmentPlan: {
            "id": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
            "installments": 2
        }
    },
    {
        id: "d06050b5-1364-47b6-ac7e-a3b652f32885",
        moment: today.toString(),
        amount: 780.00,
        description: "Amazon",
        title: null,
        type: StatementType.DEPOSIT,
        method: "ACCOUNT",
        financialMovement: FinancialMovementType.DEBIT,
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
        id: "d16050b5-1364-47b6-ac7e-a3b652f32881",
        moment: yesterday.toString(),
        amount: 2310.120,
        description: "Renner",
        title: null,
        type: StatementType.PAYMENT,
        method: "ACCOUNT",
        financialMovement: FinancialMovementType.DEBIT,
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
        },
        installmentPlan: {
            "id": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
            "installments": 12
        }
    },
    {
        id: "da6050b5-1364-47b6-ac7e-a3b652f3288a",
        moment: new Date(new Date().setDate(yesterday.getDate() - 1)).toString(),
        title: null,
        amount: 22.00,
        description: "Pagamento de fatura",
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
        moment: new Date(new Date().setDate(yesterday.getDate() - 1)).toString(),
        title: null,
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
        },
        installmentPlan: {
            "id": "8f181bd0-5987-4c32-ba5e-de1ebcfc070e",
            "installments": 1
        }
    },
    {
        id: "1bf6cdd1-259e-4ecd-91f6-f47d8c823eba",
        moment: new Date(new Date().setDate(yesterday.getDate() - 2)).toString(),
        amount: 10.00,
        title: null,
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
        id: "27cc4bbb-11cb-4e71-87d6-04a18ce67586",
        moment: "2023-12-27T14:28:45.704756",
        amount: 5643.00,
        description: "nao quero mais",
        title: null,
        type: StatementType.RETURN,
        method: "PIX",
        financialMovement: FinancialMovementType.DEBIT,
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
        id: "da6050b5-1364-47b6-ac7e-a3b652f3238a",
        moment: "2023-11-11T14:01:06.657712",
        amount: 20.00,
        description: "SEI LA",
        title: null,
        type: StatementType.DEPOSIT,
        method: "ACCOUNT",
        financialMovement: FinancialMovementType.DEBIT,
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
        id: "37cc4bbb-11cb-4e71-87d6-04a28ce6b546",
        moment: "2023-10-27T14:28:45.704756",
        amount: 10.00,
        description: "nao quero mais nao",
        title: null,
        type: StatementType.RETURN,
        method: "PIX",
        financialMovement: FinancialMovementType.DEBIT,
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
        id: "8bf6cdd1-259e-4ecd-91f6-f47d8c823e5a",
        moment: "2023-09-09T20:21:04.759255",
        amount: 10.00,
        description: "aliabba",
        title: null,
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
        id: "1bf6cdd1-2d9e-4ecd-91f6-f47d8c823e8a",
        moment: "2023-08-07T20:21:04.759255",
        amount: 10.00,
        description: "yo!",
        title: null,
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
        id: "27cc4bbb-11aab-4e71-87d6-04a28ce6b516",
        moment: "2023-07-27T14:28:45.704756",
        amount: 10.00,
        title: null,
        description: "nao quero mais",
        type: StatementType.RETURN,
        method: "PIX",
        financialMovement: FinancialMovementType.DEBIT,
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
        id: "1bf6cdd1-2d9e-4ecd-91f6-f47d8c823e1a",
        moment: "2023-06-01T20:21:04.759255",
        amount: 10.00,
        description: "yo!",
        title: null,
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
]