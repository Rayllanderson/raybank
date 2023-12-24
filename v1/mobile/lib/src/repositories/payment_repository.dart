

import 'package:dio/dio.dart';
import 'package:Raybank/src/models/payment_model.dart';
import 'package:Raybank/src/repositories/util.dart';
import 'package:Raybank/src/services/api.dart';

class PaymentRepository{

  final dio = Dio();

  Future<void> payBoleto(PaymentModel model) async {
    dio.options.headers = await getAuthHeaders();
    await dio.post(payBoletoUrl, data: model.toJson());
  }

  Future<void> payWithCreditCard(PaymentModel model) async {
    dio.options.headers = await getAuthHeaders();
    await dio.post(payCreditCardUrl, data: model.toJson());
  }

  Future<void> payInvoice(PaymentModel model) async {
    dio.options.headers = await getAuthHeaders();
    await dio.post(payInvoiceUrl, data: model.toJson());
  }
}