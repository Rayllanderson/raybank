# InvoicesApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**findAll**](InvoicesApi.md#findAll) | **GET** /api/v1/internal/accounts/{accountId}/cards/{cardId}/invoices |  |
| [**findAll1**](InvoicesApi.md#findAll1) | **GET** /api/v1/internal/accounts/{accountId}/cards/{cardId}/invoices/{invoiceId} |  |
| [**payInvoice**](InvoicesApi.md#payInvoice) | **POST** /api/v1/internal/accounts/{accountId}/cards/{cardId}/invoices/current/pay |  |
| [**payInvoiceById**](InvoicesApi.md#payInvoiceById) | **POST** /api/v1/internal/accounts/{accountId}/cards/{cardId}/invoices/{invoiceId}/pay |  |


<a name="findAll"></a>
# **findAll**
> List findAll(accountId, cardId)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **accountId** | **String**|  | [default to null] |
| **cardId** | **String**|  | [default to null] |

### Return type

[**List**](../Models/FindInvoiceListResponse.md)


### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="findAll1"></a>
# **findAll1**
> FindInvoiceResponse findAll1(accountId, cardId, invoiceId)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **accountId** | **String**|  | [default to null] |
| **cardId** | **String**|  | [default to null] |
| **invoiceId** | **String**|  | [default to null] |

### Return type

[**FindInvoiceResponse**](../Models/FindInvoiceResponse.md)


### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="payInvoice"></a>
# **payInvoice**
> InvoiceCreditResponse payInvoice(accountId, cardId, InvoiceCreditRequest)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **accountId** | **String**|  | [default to null] |
| **cardId** | **String**|  | [default to null] |
| **InvoiceCreditRequest** | [**InvoiceCreditRequest**](../Models/InvoiceCreditRequest.md)|  | |

### Return type

[**InvoiceCreditResponse**](../Models/InvoiceCreditResponse.md)


### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="payInvoiceById"></a>
# **payInvoiceById**
> InvoiceCreditResponse payInvoiceById(accountId, cardId, invoiceId, InvoiceCreditRequest)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **accountId** | **String**|  | [default to null] |
| **cardId** | **String**|  | [default to null] |
| **invoiceId** | **String**|  | [default to null] |
| **InvoiceCreditRequest** | [**InvoiceCreditRequest**](../Models/InvoiceCreditRequest.md)|  | |

### Return type

[**InvoiceCreditResponse**](../Models/InvoiceCreditResponse.md)


### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

