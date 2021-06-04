

import 'package:dio/dio.dart';
import 'package:mobile/src/models/bank_account_model.dart';
import 'package:mobile/src/models/transfer_model.dart';
import 'package:mobile/src/repositories/util.dart';
import 'package:mobile/src/services/api.dart';

class BankAccountRepository{

  final dio = Dio();

  Future<BankAccountModel> fetchBankAccount() async {
    dio.options.headers = await getAuthHeaders();
    var response = await dio.get(bankAccountUrl);
    return BankAccountModel.fromJson(response.data);
  }

  Future<void> transfer(TransferModel transaction) async {
    dio.options.headers = await getAuthHeaders();
    await dio.post(bankAccountUrl + "/transfer", data: transaction);
  }
}