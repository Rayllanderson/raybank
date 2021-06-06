import 'package:mobile/src/services/bank_account_service.dart';
import 'package:mobile/src/utils/mask_util.dart';

class DepositController {

  final moneyController;

  DepositController(this.moneyController);

  BankAccountService service = new BankAccountService();

  void deposit(){
    double value = double.tryParse(unmaskMoney(moneyController.text)) ?? 0.0;
    if (value == 0.0) return;
    service.deposit(value);
  }

}