import 'package:flutter/material.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/utils/mask_util.dart';

class TransferController {

  final moneyController;
  final receiverController;

  TransferController(this.moneyController, this.receiverController);

  void goToSelectContactPage(){
    double value = double.tryParse(unmaskMoney(moneyController.text)) ?? 0.0;
    if (isAmountInvalid() || value == 0)
      return;

    //TODO setar o valor no objeto depois

    Navigator.pushNamed(navigatorKey.currentContext, '/transfer');
  }

  /// verifica se o valor passado é menor que o valor disponível na conta
  bool isAmountInvalid(){
    double value = double.tryParse(unmaskMoney(moneyController.text)) ?? 0.0;
    double accountBalance = 550.59; //dado mockado
    return value > accountBalance;
  }
}