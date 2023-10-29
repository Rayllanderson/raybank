# ContactsApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**findAllByOwnerId**](ContactsApi.md#findAllByOwnerId) | **GET** /api/v1/internal/accounts/{accountId}/contacts |  |
| [**findById2**](ContactsApi.md#findById2) | **GET** /api/v1/internal/accounts/{accountId}/contacts/{contactId} |  |


<a name="findAllByOwnerId"></a>
# **findAllByOwnerId**
> Object findAllByOwnerId(accountId)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **accountId** | **String**|  | [default to null] |

### Return type

**Object**


### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="findById2"></a>
# **findById2**
> Object findById2(accountId, contactId)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **accountId** | **String**|  | [default to null] |
| **contactId** | **String**|  | [default to null] |

### Return type

**Object**

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

