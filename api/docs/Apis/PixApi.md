# PixApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteByKey**](PixApi.md#deleteByKey) | **DELETE** /api/v1/internal/pix/keys/{key} |  |
| [**doReturn**](PixApi.md#doReturn) | **POST** /api/v1/internal/pix/return |  |
| [**findAllByAccountId**](PixApi.md#findAllByAccountId) | **GET** /api/v1/internal/pix/keys |  |
| [**findById**](PixApi.md#findById) | **GET** /api/v1/internal/pix/qrcode/{idOrCode} |  |
| [**findById3**](PixApi.md#findById3) | **GET** /api/v1/external/pix/{id} |  |
| [**findByKey**](PixApi.md#findByKey) | **GET** /api/v1/internal/pix/keys/{key} |  |
| [**findLimit**](PixApi.md#findLimit) | **GET** /api/v1/internal/pix/limits |  |
| [**generate**](PixApi.md#generate) | **POST** /api/v1/internal/pix/qrcode |  |
| [**register1**](PixApi.md#register1) | **POST** /api/v1/internal/pix/keys |  |
| [**transfer**](PixApi.md#transfer) | **POST** /api/v1/internal/pix/transfer |  |
| [**transfer1**](PixApi.md#transfer1) | **POST** /api/v1/internal/pix/payment |  |
| [**update**](PixApi.md#update) | **PATCH** /api/v1/internal/pix/limits |  |


<a name="deleteByKey"></a>
# **deleteByKey**
> Object deleteByKey(key)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **key** | **String**|  | [default to null] |

### Return type

**Object**



### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="doReturn"></a>
# **doReturn**
> PixReturnResponse doReturn(PixReturnRequest)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **PixReturnRequest** | [**PixReturnRequest**](../Models/PixReturnRequest.md)|  | |

### Return type

[**PixReturnResponse**](../Models/PixReturnResponse.md)


### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="findAllByAccountId"></a>
# **findAllByAccountId**
> FindListPixKeyResponse findAllByAccountId()



### Parameters
This endpoint does not need any parameter.

### Return type

[**FindListPixKeyResponse**](../Models/FindListPixKeyResponse.md)

### Authorization



### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="findById"></a>
# **findById**
> Object findById(idOrCode)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **idOrCode** | **String**|  | [default to null] |

### Return type

**Object**


### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="findById3"></a>
# **findById3**
> Object findById3(id)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | [default to null] |

### Return type

**Object**


### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="findByKey"></a>
# **findByKey**
> FindPixKeyResponse findByKey(key)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **key** | **String**|  | [default to null] |

### Return type

[**FindPixKeyResponse**](../Models/FindPixKeyResponse.md)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="findLimit"></a>
# **findLimit**
> Object findLimit()



### Parameters
This endpoint does not need any parameter.

### Return type

**Object**

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="generate"></a>
# **generate**
> GenerateQrCodeResponse generate(GenerateQrCodeRequest)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **GenerateQrCodeRequest** | [**GenerateQrCodeRequest**](../Models/GenerateQrCodeRequest.md)|  | |

### Return type

[**GenerateQrCodeResponse**](../Models/GenerateQrCodeResponse.md)


### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="register1"></a>
# **register1**
> RegisterPixResponse register1(RegisterPixKeyRequest)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **RegisterPixKeyRequest** | [**RegisterPixKeyRequest**](../Models/RegisterPixKeyRequest.md)|  | |

### Return type

[**RegisterPixResponse**](../Models/RegisterPixResponse.md)


### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="transfer"></a>
# **transfer**
> PixTransferResponse transfer(PixTransferRequest)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **PixTransferRequest** | [**PixTransferRequest**](../Models/PixTransferRequest.md)|  | |

### Return type

[**PixTransferResponse**](../Models/PixTransferResponse.md)


### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="transfer1"></a>
# **transfer1**
> PixPaymentResponse transfer1(PixPaymentRequest)


### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **PixPaymentRequest** | [**PixPaymentRequest**](../Models/PixPaymentRequest.md)|  | |

### Return type

[**PixPaymentResponse**](../Models/PixPaymentResponse.md)


### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="update"></a>
# **update**
> Object update(UpdatePixLimitRequest)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **UpdatePixLimitRequest** | [**UpdatePixLimitRequest**](../Models/UpdatePixLimitRequest.md)|  | |

### Return type

**Object**

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

