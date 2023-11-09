# FindTransactionControllerApi

All URIs are relative to *http://localhost:8080*

| Method                                                    | HTTP request                             | Description |
|-----------------------------------------------------------|------------------------------------------|-------------|
| [**findAll**](FindTransactionControllerApi.md#findAll2)   | **GET** /api/v1/admin/transactions       |             |
| [**findById**](FindTransactionControllerApi.md#findById4) | **GET** /api/v1/admin/transactions/{tId} |             |

<a name="findAll2"></a>

# **findAll2**

> PaginationTransaction findAll2(accountId, pageable)

### Parameters

| Name          | Type                          | Description | Notes             |
|---------------|-------------------------------|-------------|-------------------|
| **accountId** | **String**                    |             | [default to null] |
| **pageable**  | [**Pageable**](../Models/.md) |             | [default to null] |

### Return type

[**PaginationTransaction**](../Models/PaginationTransaction.md)


### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="findById4"></a>

# **findById4**

> Transaction findById4(tId)

### Parameters

| Name    | Type       | Description | Notes             |
|---------|------------|-------------|-------------------|
| **tId** | **String** |             | [default to null] |

### Return type

[**Transaction**](../Models/Transaction.md)


### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

