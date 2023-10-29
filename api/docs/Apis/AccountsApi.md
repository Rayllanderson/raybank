# AccountsApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**findAuthenticated**](AccountsApi.md#findAuthenticated) | **GET** /api/v1/internal/accounts/authenticated |  |
| [**findById1**](AccountsApi.md#findById1) | **GET** /api/v1/internal/accounts/{accountId} |  |
| [**transfer2**](AccountsApi.md#transfer2) | **POST** /api/v1/internal/accounts/{accountId}/transfer |  |


<a name="findAuthenticated"></a>
# **findAuthenticated**
> Object findAuthenticated()


### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="findById1"></a>
# **findById1**
> Object findById1(accountId)


### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **accountId** | **String**|  | [default to null] |

### Return type

**Object**


### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="transfer2"></a>
# **transfer2**
> Object transfer2(accountId, BankAccountTransferRequest)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **accountId** | **String**|  | [default to null] |
| **BankAccountTransferRequest** | [**BankAccountTransferRequest**](../Models/BankAccountTransferRequest.md)|  | |

### Return type

**Object**


### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

