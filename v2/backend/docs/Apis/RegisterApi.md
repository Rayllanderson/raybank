# RegisterApi

All URIs are relative to *http://localhost:8080*

| Method                                    | HTTP request                             | Description |
|-------------------------------------------|------------------------------------------|-------------|
| [**register**](RegisterApi.md#register)   | **POST** /api/v1/users/register          |             |
| [**register2**](RegisterApi.md#register2) | **POST** /api/v1/establishments/register |             |

<a name="register"></a>

# **register**

> Object register(RegisterUserRequest)

### Parameters

| Name                    | Type                                                        | Description | Notes |
|-------------------------|-------------------------------------------------------------|-------------|-------|
| **RegisterUserRequest** | [**RegisterUserRequest**](../Models/RegisterUserRequest.md) |             |       |

### Return type

**Object**

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

<a name="register2"></a>

# **register2**

> Object register2(RegisterEstablishmentRequest)

### Parameters

| Name                             | Type                                                                          | Description | Notes |
|----------------------------------|-------------------------------------------------------------------------------|-------------|-------|
| **RegisterEstablishmentRequest** | [**RegisterEstablishmentRequest**](../Models/RegisterEstablishmentRequest.md) |             |       |

### Return type

**Object**

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

