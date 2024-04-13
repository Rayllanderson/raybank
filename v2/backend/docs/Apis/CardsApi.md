# CardsApi

All URIs are relative to *http://localhost:8080*

| Method                           | HTTP request                                                         | Description |
|----------------------------------|----------------------------------------------------------------------|-------------|
| [**change**](CardsApi.md#change) | **PATCH** /api/v1/internal/accounts/{accountId}/cards/{cardId}/limit |             |
| [**create**](CardsApi.md#create) | **POST** /api/v1/internal/accounts/{accountId}/cards                 |             |
| [**find3**](CardsApi.md#find3)   | **GET** /api/v1/internal/accounts/{accountId}/cards/{cardId}         |             |
| [**pay1**](CardsApi.md#pay1)     | **POST** /api/v1/external/cards/payment                              |             |

<a name="change"></a>

# **change**

> Object change(accountId, cardId, ChangeCardLimitRequest)

### Parameters

| Name                       | Type                                                              | Description | Notes             |
|----------------------------|-------------------------------------------------------------------|-------------|-------------------|
| **accountId**              | **String**                                                        |             | [default to null] |
| **cardId**                 | **String**                                                        |             | [default to null] |
| **ChangeCardLimitRequest** | [**ChangeCardLimitRequest**](../Models/ChangeCardLimitRequest.md) |             |                   |

### Return type

**Object**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="create"></a>

# **create**

> Object create(accountId, CreateCreditCardRequest)

### Parameters

| Name                        | Type                                                                | Description | Notes             |
|-----------------------------|---------------------------------------------------------------------|-------------|-------------------|
| **accountId**               | **String**                                                          |             | [default to null] |
| **CreateCreditCardRequest** | [**CreateCreditCardRequest**](../Models/CreateCreditCardRequest.md) |             |                   |

### Return type

**Object**

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="find3"></a>

# **find3**

> Object find3(accountId, cardId, sensitive)

### Parameters

| Name          | Type        | Description | Notes                         |
|---------------|-------------|-------------|-------------------------------|
| **accountId** | **String**  |             | [default to null]             |
| **cardId**    | **String**  |             | [default to null]             |
| **sensitive** | **Boolean** |             | [optional] [default to false] |

### Return type

**Object**

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="pay1"></a>

# **pay1**

> CardPaymentResponse pay1(PaymentCardRequest)

### Parameters

| Name                   | Type                                                      | Description | Notes |
|------------------------|-----------------------------------------------------------|-------------|-------|
| **PaymentCardRequest** | [**PaymentCardRequest**](../Models/PaymentCardRequest.md) |             |       |

### Return type

[**CardPaymentResponse**](../Models/CardPaymentResponse.md)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

