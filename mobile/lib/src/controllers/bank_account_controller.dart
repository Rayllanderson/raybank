
import 'package:mobile/src/models/bank_account_model.dart';
import 'package:mobile/src/repositories/bank_account_repository.dart';

class BankAccountController {

  BankAccountModel bankAccountModel;
  BankAccountRepository repository = new BankAccountRepository();

  Future<void> fetchBankAccount() async {
    await repository.fetchBankAccount().then((data) {
      bankAccountModel = data;
    });

  }

}