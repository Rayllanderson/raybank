final String baseApiUrl = "http://192.168.0.122:8080/api/v1";
final String registerUrl = baseApiUrl + "/users";
final String loginUrl = baseApiUrl + "/login";
final String bankAccountUrl = baseApiUrl + "/users/authenticated/bank-account";
final String creditCardUrl = baseApiUrl + "/users/authenticated/bank-account/credit-card";
final String payBoletoUrl = baseApiUrl + "/users/authenticated/payment/boleto";
final String payCreditCardUrl = baseApiUrl + "/users/authenticated/payment/credit-card";
final String payInvoiceUrl = baseApiUrl + "/users/authenticated/bank-account/credit-card/pay/invoice";