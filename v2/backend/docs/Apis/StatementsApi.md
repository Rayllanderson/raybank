# StatementsApi

All URIs are relative to *http://localhost:8080*

| Method                                                              | HTTP request                                                           | Description |
|---------------------------------------------------------------------|------------------------------------------------------------------------|-------------|
| [**findAllStatements**](StatementsApi.md#findAllStatements)         | **GET** /api/v1/internal/accounts/{accountId}/statements               |             |
| [**findBankStatementById**](StatementsApi.md#findBankStatementById) | **GET** /api/v1/internal/accounts/{accountId}/statements/{statementId} |             |

<a name="findAllStatements"></a>

# **findAllStatements**

> Object findAllStatements(accountId, type, page, size, sort)

### Parameters

| Name          | Type                            | Description | Notes                                                         |
|---------------|---------------------------------|-------------|---------------------------------------------------------------|
| **accountId** | **String**                      |             | [default to null]                                             |
| **type**      | **String**                      |             | [optional] [default to ALL] [enum: ALL, CARD, ACCOUNT]        |
| **page**      | **Integer**                     |             | [optional] [default to 0]                                     |
| **size**      | **Integer**                     |             | [optional] [default to 50]                                    |
| **sort**      | [**List**](../Models/String.md) |             | [optional] [default to [&quot;moment&quot;,&quot;desc&quot;]] |

### Return type

**Object**

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

<a name="findBankStatementById"></a>

# **findBankStatementById**

> Object findBankStatementById(accountId, statementId)

### Parameters

| Name            | Type       | Description | Notes             |
|-----------------|------------|-------------|-------------------|
| **accountId**   | **String** |             | [default to null] |
| **statementId** | **String** |             | [default to null] |

### Return type

**Object**

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*

