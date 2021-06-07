import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:Raybank/main.dart';
import 'package:Raybank/src/components/alerts/alert.dart';
import 'package:Raybank/src/models/erro_model.dart';
import 'package:Raybank/src/models/payment_model.dart';
import 'package:Raybank/src/repositories/payment_repository.dart';
import 'package:Raybank/src/utils/throw_error.dart';

class PaymentService {
  final PaymentRepository repository = new PaymentRepository();

  Future<void> payBoleto(PaymentModel model) async {
    try {
      await repository.payBoleto(model);
      Navigator.of(navigatorKey.currentContext).pushReplacementNamed('/home');
      Alert.displaySimpleAlert("Sucesso", "Sua pagamento via boleto foi aprovado!");
    } on DioError catch (e) {
      ApiError err = catchError(e);
      Alert.displaySimpleAlert(err.title, err.message);
      return null;
    }
  }

  Future<void> payWithCreditCard(PaymentModel model) async {
    try {
      await repository.payWithCreditCard(model);
      Navigator.of(navigatorKey.currentContext).pushReplacementNamed('/home');
      Alert.displaySimpleAlert("Sucesso", "Sua compra no cartão de crédito foi aprovada!");
    } on DioError catch (e) {
      ApiError err = catchError(e);
      Alert.displaySimpleAlert(err.title, err.message);
      return null;
    }
  }

  Future<void> payInvoice(PaymentModel model) async {
    try {
      await repository.payInvoice(model);
      Navigator.of(navigatorKey.currentContext).pushReplacementNamed('/home');
      Alert.displaySimpleAlert("Pronto", "Sua fatura foi paga com sucesso!");
    } on DioError catch (e) {
      ApiError err = catchError(e);
      Alert.displaySimpleAlert(err.title, err.message);
      return null;
    }
  }
}
