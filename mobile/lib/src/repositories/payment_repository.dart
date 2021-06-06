

import 'package:dio/dio.dart';
import 'package:mobile/src/repositories/util.dart';
import 'package:mobile/src/services/api.dart';

class PaymentRepository{

  final dio = Dio();

  Future<void> payBoleto(double amount) async {
    dio.options.headers = await getAuthHeaders();
    await dio.post(payBoletoUrl, data: amount);
  }

  Future<void> payWithCreditCard(double amount) async {
    dio.options.headers = await getAuthHeaders();
    await dio.post(creditCardUrl, data: amount);
  }

  Future<void> payInvoice(double amount) async {
    dio.options.headers = await getAuthHeaders();
    await dio.post(payInvoiceUrl, data: amount);
  }
}