import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/components/alerts/alert.dart';
import 'package:mobile/src/models/bank_account_model.dart';
import 'package:mobile/src/models/erro_model.dart';
import 'package:mobile/src/models/statement_model.dart';
import 'package:mobile/src/models/transfer_model.dart';
import 'package:mobile/src/repositories/bank_account_repository.dart';
import 'package:mobile/src/utils/string_util.dart';
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

  Future<List<StatementModel>> getAllStatements() async {
    try {
      return await repository.fetchStatements();
    } on DioError {
      Alert.displaySimpleAlert("Ocorreu um erro", "Não foi possível carregar seus dados.");
      return List.empty();
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

  void deposit(double value) async {
    try {
      await repository.deposit(value);
      Navigator.of(navigatorKey.currentContext).pushReplacementNamed("/home");
      Alert.displaySimpleAlert("Sucesso", "Você realizou um deposito no valor de ${convertToBRL(value)}");
    } on DioError catch (e) {
      ApiError err = catchError(e.response);
      Alert.displaySimpleAlert(err.title, err.message);
    }
  }
}
