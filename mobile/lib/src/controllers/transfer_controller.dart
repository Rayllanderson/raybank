import 'package:mobile/src/utils/mask_util.dart';

class TransferController {

  final moneyController;
  final receiverController;

  TransferController(this.moneyController, this.receiverController);

  void goToSelectContactPage(){
    // verificar se o dinheiro a ser transferido é menor ou igual . //criar um método maybe
    // Se for, deixa passar, senao, alert
    // verificar se é igual a 0, se for, alert
  }

  /// verifica se o valor passado é menor que o valor disponível na conta
  bool isAmountInvalid(){
    double value = double.tryParse(unmaskMoney(moneyController.text)) ?? 0.0;
    double accountBalance = 550.59; //dado mockado
    return value > accountBalance;
  }
}