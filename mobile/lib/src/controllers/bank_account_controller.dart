import 'package:mobile/src/models/bank_account_model.dart';
import 'package:mobile/src/services/bank_account_service.dart';

class BankAccountController {

  BankAccountModel bankAccountModel = BankAccountModel.empty();
  final BankAccountService accountService = new BankAccountService();

  Future<BankAccountModel> fetchBankAccount() async {
    bankAccountModel = await accountService.getBankAccount();
    return accountService.getBankAccount();
  }
}
