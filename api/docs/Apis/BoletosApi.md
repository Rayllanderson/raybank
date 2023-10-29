# BoletosApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**find2**](BoletosApi.md#find2) | **GET** /api/v1/internal/boletos/{barCode} |  |
| [**generateBoleto**](BoletosApi.md#generateBoleto) | **POST** /api/v1/internal/boletos |  |
| [**pay**](BoletosApi.md#pay) | **POST** /api/v1/internal/boletos/payment |  |


<a name="find2"></a>
# **find2**
> BoletoDetailsResponse find2(barCode)



### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **barCode** | **String**|  | [default to null] |

### Return type

[**BoletoDetailsResponse**](../Models/BoletoDetailsResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="generateBoleto"></a>
# **generateBoleto**
> GenerateBoletoResponse generateBoleto(GenerateBoletoRequest)


### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **GenerateBoletoRequest** | [**GenerateBoletoRequest**](../Models/GenerateBoletoRequest.md)|  | |

### Return type

[**GenerateBoletoResponse**](../Models/GenerateBoletoResponse.md)


### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="pay"></a>
# **pay**
> BoletoPaymentResponse pay(BoletoPaymentRequest)


### Parameters

|Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **BoletoPaymentRequest** | [**BoletoPaymentRequest**](../Models/BoletoPaymentRequest.md)|  | |

### Return type

[**BoletoPaymentResponse**](../Models/BoletoPaymentResponse.md)


### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

