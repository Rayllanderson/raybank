# Documentation for RayBank API Doc

<a name="documentation-for-api-endpoints"></a>

## Documentation for API Endpoints


| API                | Method                                                                            | HTTP request                                                                           |
|--------------------|-----------------------------------------------------------------------------------|----------------------------------------------------------------------------------------|
| *Accounts*         | [**find account authenticated**](docs/Apis/AccountsApi.md#findauthenticated)      | **GET** /api/v1/internal/accounts/authenticated                                        |
| *Accounts*         | [**find account by id**](docs/Apis/AccountsApi.md#findbyid1)                      | **GET** /api/v1/internal/accounts/{accountId}                                          |
| *Accounts*         | [**transfer with account**](docs/Apis/AccountsApi.md#transfer2)                   | **POST** /api/v1/internal/accounts/{accountId}/transfer                                |
| -                  | -                                                                                 | -                                                                                      |
| *Cards*            | [**create card**](docs/Apis/CardsApi.md#create)                                   | **POST** /api/v1/internal/accounts/{accountId}/cards                                   |
| *Cards*            | [**pay with card**](docs/Apis/CardsApi.md#pay1)                                   | **POST** /api/v1/external/cards/payment                                                |
| *Cards*            | [**find card by id**](docs/Apis/CardsApi.md#find3)                                | **GET** /api/v1/internal/accounts/{accountId}/cards/{cardId}                           |
| *Cards*            | [**change card limit**](docs/Apis/CardsApi.md#change)                             | **PATCH** /api/v1/internal/accounts/{accountId}/cards/{cardId}/limit                   |
| -                  | -                                                                                 | -                                                                                      |
| *Pix*              | [**register pix key**](docs/Apis/PixApi.md#register1)                             | **POST** /api/v1/internal/pix/keys                                                     |
| *Pix*              | [**transfer with pix**](docs/Apis/PixApi.md#transfer)                             | **POST** /api/v1/internal/pix/transfer                                                 |
| *Pix*              | [**generate qr code**](docs/Apis/PixApi.md#generate)                              | **POST** /api/v1/internal/pix/qrcode                                                   |
| *Pix*              | [**pay with pix**](docs/Apis/PixApi.md#transfer1)                                 | **POST** /api/v1/internal/pix/payment                                                  |
| *Pix*              | [**return pix**](docs/Apis/PixApi.md#doreturn)                                    | **POST** /api/v1/internal/pix/return                                                   |
| *Pix*              | [**find qrcode by id**](docs/Apis/PixApi.md#findbyid)                             | **GET** /api/v1/internal/pix/qrcode/{idOrCode}                                         |
| *Pix*              | [**find key by id**](docs/Apis/PixApi.md#findbykey)                               | **GET** /api/v1/internal/pix/keys/{key}                                                |
| *Pix*              | [**find all keys by accountId**](docs/Apis/PixApi.md#findallbyaccountid)          | **GET** /api/v1/internal/pix/keys                                                      |
| *Pix*              | [**find pix by e2eid**](docs/Apis/PixApi.md#findbyid3)                            | **GET** /api/v1/external/pix/{id}                                                      |
| *Pix*              | [**find pix limit**](docs/Apis/PixApi.md#findlimit)                               | **GET** /api/v1/internal/pix/limits                                                    |
| *Pix*              | [**update pix limit**](docs/Apis/PixApi.md#update)                                | **PATCH** /api/v1/internal/pix/limits                                                  |
| *Pix*              | [**delete pix key**](docs/Apis/PixApi.md#deletebykey)                             | **DELETE** /api/v1/internal/pix/keys/{key}                                             |
| -                  | -                                                                                 | -                                                                                      |
| *Boletos*          | [**generate boleto**](docs/Apis/BoletosApi.md#generateboleto)                     | **POST** /api/v1/internal/boletos                                                      |
| *Boletos*          | [**find boleto**](docs/Apis/BoletosApi.md#find2)                                  | **GET** /api/v1/internal/boletos/{barCode}                                             |
| *Boletos*          | [**pay with boleto**](docs/Apis/BoletosApi.md#pay)                                | **POST** /api/v1/internal/boletos/payment                                              |
| -                  | -                                                                                 | -                                                                                      |
| *InstallmentPlan*  | [**find installment plan**](docs/Apis/InstallmentPlanApi.md#find1)                | **GET** /api/v1/internal/installment-plans/{planId}                                    |
| -                  | -                                                                                 | -                                                                                      |
| *Invoices*         | [**find all invoices**](docs/Apis/InvoicesApi.md#findall)                         | **GET** /api/v1/internal/accounts/{accountId}/cards/{cardId}/invoices                  |
| *Invoices*         | [**find invoice by id**](docs/Apis/InvoicesApi.md#findall1)                       | **GET** /api/v1/internal/accounts/{accountId}/cards/{cardId}/invoices/{invoiceId}      |
| *Invoices*         | [**pay current invoice with account**](docs/Apis/InvoicesApi.md#payinvoice)       | **POST** /api/v1/internal/accounts/{accountId}/cards/{cardId}/invoices/current/pay     |
| *Invoices*         | [**pay invoice by Id with account**](docs/Apis/InvoicesApi.md#payinvoicebyid)     | **POST** /api/v1/internal/accounts/{accountId}/cards/{cardId}/invoices/{invoiceId}/pay |
| -                  | -                                                                                 | -                                                                                      |
| *RefundController* | [**refund transaction**](docs/Apis/RefundControllerApi.md#find)                   | **POST** /api/v1/external/transactions/{transactionId}/refund                          |
| -                  | -                                                                                 | -                                                                                      |
| *Register*         | [**register user**](docs/Apis/RegisterApi.md#register)                            | **POST** /api/v1/users/register                                                        |
| *Register*         | [**register establishment**](docs/Apis/RegisterApi.md#register2)                  | **POST** /api/v1/establishments/register                                               |
| -                  | -                                                                                 | -                                                                                      |
| *Contacts*         | [**find contact by id**](docs/Apis/ContactsApi.md#findbyid2)                      | **GET** /api/v1/internal/accounts/{accountId}/contacts/{contactId}                     |
| *Contacts*         | [**find all contacts by account id**](docs/Apis/ContactsApi.md#findallbyownerid)  | **GET** /api/v1/internal/accounts/{accountId}/contacts                                 |
| -                  | -                                                                                 | -                                                                                      |
| *BankStatements*   | [**find all bank statements**](docs/Apis/StatementsApi.md#findallstatements)      | **GET** /api/v1/internal/accounts/{accountId}/statements                               |
| *BankStatements*   | [**find bank statement by id**](docs/Apis/StatementsApi.md#findbankstatementbyid) | **GET** /api/v1/internal/accounts/{accountId}/statements/{statementId}                 |

<a name="documentation-for-models"></a>

## Documentation for Models

- [BankAccountTransferRequest](docs/Models/BankAccountTransferRequest.md)
- [BeneficiaryInput](docs/Models/BeneficiaryInput.md)
- [BoletoAccountResponse](docs/Models/BoletoAccountResponse.md)
- [BoletoBeneficiaryResponse](docs/Models/BoletoBeneficiaryResponse.md)
- [BoletoDetailsResponse](docs/Models/BoletoDetailsResponse.md)
- [BoletoInstitutionIssuingResponse](docs/Models/BoletoInstitutionIssuingResponse.md)
- [BoletoInvoiceResponse](docs/Models/BoletoInvoiceResponse.md)
- [BoletoPaymentRequest](docs/Models/BoletoPaymentRequest.md)
- [BoletoPaymentResponse](docs/Models/BoletoPaymentResponse.md)
- [BoletoResponse](docs/Models/BoletoResponse.md)
- [Card](docs/Models/Card.md)
- [CardPaymentResponse](docs/Models/CardPaymentResponse.md)
- [Card_expiryDate](docs/Models/Card_expiryDate.md)
- [ChangeCardLimitRequest](docs/Models/ChangeCardLimitRequest.md)
- [CreateCreditCardRequest](docs/Models/CreateCreditCardRequest.md)
- [Credit](docs/Models/Credit.md)
- [Debit](docs/Models/Debit.md)
- [Destination](docs/Models/Destination.md)
- [FindInvoiceListResponse](docs/Models/FindInvoiceListResponse.md)
- [FindInvoiceResponse](docs/Models/FindInvoiceResponse.md)
- [FindListPixKeyResponse](docs/Models/FindListPixKeyResponse.md)
- [FindPixKeyResponse](docs/Models/FindPixKeyResponse.md)
- [GenerateBoletoRequest](docs/Models/GenerateBoletoRequest.md)
- [GenerateBoletoResponse](docs/Models/GenerateBoletoResponse.md)
- [GenerateQrCodeRequest](docs/Models/GenerateQrCodeRequest.md)
- [GenerateQrCodeResponse](docs/Models/GenerateQrCodeResponse.md)
- [InvoiceCreditRequest](docs/Models/InvoiceCreditRequest.md)
- [InvoiceCreditResponse](docs/Models/InvoiceCreditResponse.md)
- [InvoiceTransactionResponse](docs/Models/InvoiceTransactionResponse.md)
- [Payment](docs/Models/Payment.md)
- [PaymentCardRequest](docs/Models/PaymentCardRequest.md)
- [PixPaymentRequest](docs/Models/PixPaymentRequest.md)
- [PixPaymentResponse](docs/Models/PixPaymentResponse.md)
- [PixReturnRequest](docs/Models/PixReturnRequest.md)
- [PixReturnResponse](docs/Models/PixReturnResponse.md)
- [PixTransferRequest](docs/Models/PixTransferRequest.md)
- [PixTransferResponse](docs/Models/PixTransferResponse.md)
- [RefundRequest](docs/Models/RefundRequest.md)
- [RefundResponse](docs/Models/RefundResponse.md)
- [RegisterEstablishmentRequest](docs/Models/RegisterEstablishmentRequest.md)
- [RegisterPixKeyRequest](docs/Models/RegisterPixKeyRequest.md)
- [RegisterPixResponse](docs/Models/RegisterPixResponse.md)
- [RegisterUserRequest](docs/Models/RegisterUserRequest.md)
- [UpdatePixLimitRequest](docs/Models/UpdatePixLimitRequest.md)

<a name="documentation-for-authorization"></a>

## Documentation for Authorization

All endpoints require authorization.

## Reference

This doc was generated by [open-api-generator](https://openapi-generator.tech/) 