

import 'package:dio/dio.dart';
import 'package:Raybank/src/models/bank_account_model.dart';
import 'package:Raybank/src/models/contact_model.dart';
import 'package:Raybank/src/models/statement_model.dart';
import 'package:Raybank/src/models/transfer_model.dart';
import 'package:Raybank/src/repositories/util.dart';
import 'package:Raybank/src/services/api.dart';

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

  Future<void> deposit(double amount) async {
    dio.options.headers = await getAuthHeaders();
    var data = Map<String, double>();
    data['amount'] = amount;
    await dio.post(bankAccountUrl + "/deposit", data: data);
  }

  Future<List<ContactModel>> fetchContacts() async {
    dio.options.headers = await getAuthHeaders();
    final response = await dio.get(bankAccountUrl + "/contacts");
    return List.from(response.data).map((contact) => ContactModel.fromJson(contact)).toList();
  }

  Future<List<StatementModel>> fetchStatements() async {
    dio.options.headers = await getAuthHeaders();
    final response = await dio.get(bankAccountUrl + "/statements");
    return List.from(response.data).map((statement) => StatementModel.fromJson(statement)).toList();
  }
}