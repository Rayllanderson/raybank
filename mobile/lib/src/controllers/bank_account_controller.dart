
import 'package:dio/dio.dart';
import 'package:mobile/src/models/bank_account_model.dart';
import 'package:mobile/src/repositories/bank_account_repository.dart';
import 'package:mobile/src/utils/throw_error.dart';

class BankAccountController {
  BankAccountModel bankAccountModel = BankAccountModel.empty();
  BankAccountRepository repository = new BankAccountRepository();

  Future fetchBankAccount() async {
    try {
      bankAccountModel = await repository.fetchBankAccount();
    } on DioError catch (e) {
      catchError(e.response);
    }
  }
}
