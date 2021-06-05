import 'package:dio/dio.dart';
import 'package:flutter/widgets.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/components/alerts/alert.dart';
import 'package:mobile/src/models/bank_account_model.dart';
import 'package:mobile/src/models/erro_model.dart';
import 'package:mobile/src/models/transfer_model.dart';
import 'package:mobile/src/repositories/bank_account_repository.dart';
import 'package:mobile/src/utils/throw_error.dart';

class BankAccountService {
  final BankAccountRepository repository = new BankAccountRepository();

  Future<BankAccountModel> getBankAccount() async {
    try {
      return await repository.fetchBankAccount();
    } on DioError catch (e) {
      ApiError err = catchError(e.response);
      Alert.displaySimpleAlert(err.title, err.message);
      return null;
    }
  }

  void transfer(TransferModel transaction) async {
    try {
      await repository.transfer(transaction);
      Navigator.of(navigatorKey.currentContext).pushReplacementNamed("/home");
      Alert.displaySimpleAlert("Pronto!", "Você transferiu R\$ ${transaction.amount} para "
          "${transaction.toName} com sucesso!");
    } on DioError catch (e) {
      ApiError err = catchError(e.response);
      Alert.displaySimpleAlert(err.title, err.message);
    }
  }
}
