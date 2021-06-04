import 'package:flutter/material.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/models/transfer_model.dart';
import 'package:mobile/src/utils/mask_util.dart';

class TransferController {

  final moneyController;
  final receiverController;
  final available;

  TransferController(this.moneyController, this.receiverController, this.available);

  void goToSelectContactPage(){
    double value = double.tryParse(unmaskMoney(moneyController.text)) ?? 0.0;
    if (isAmountInvalid() || value == 0)
      return;

    var transaction = TransferModel(amount: value);
    storage.setTransfer(transaction);

    Navigator.pushNamed(navigatorKey.currentContext, '/transfer');
  }

  void goToMessagePage(){

  }

  /// verifica se o valor é menor que o valor disponível na conta
  bool isAmountInvalid(){
    double value = double.tryParse(unmaskMoney(moneyController.text)) ?? 0.0;
    double accountBalance = available;
    return value > accountBalance;
  }
}