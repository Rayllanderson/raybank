import 'package:dio/dio.dart';
import 'package:mobile/src/components/alerts/alert.dart';
import 'package:mobile/src/models/erro_model.dart';
import 'package:mobile/src/repositories/payment_repository.dart';
import 'package:mobile/src/utils/throw_error.dart';

class BankAccountService {
  final PaymentRepository repository = new PaymentRepository();

  Future<void> payBoleto(double amount) async {
    try {
      await repository.payBoleto(amount);
    } on DioError catch (e) {
      ApiError err = catchError(e.response);
      Alert.displaySimpleAlert(err.title, err.message);
      return null;
    }
  }

  Future<void> payWithCreditCard(double amount) async {
    try {
      await repository.payWithCreditCard(amount);
    } on DioError catch (e) {
      ApiError err = catchError(e.response);
      Alert.displaySimpleAlert(err.title, err.message);
      return null;
    }
  }

  Future<void> payInvoice(double amount) async {
    try {
      await repository.payInvoice(amount);
    } on DioError catch (e) {
      ApiError err = catchError(e.response);
      Alert.displaySimpleAlert(err.title, err.message);
      return null;
    }
  }
}
