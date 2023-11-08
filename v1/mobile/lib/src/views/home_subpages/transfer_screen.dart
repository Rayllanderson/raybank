import 'package:flutter/material.dart';
import 'package:Raybank/src/components/cards/page_card.dart';
import 'package:Raybank/src/controllers/transfer_controller.dart';
import 'package:Raybank/src/utils/actions_util.dart';
import 'package:Raybank/src/utils/creator_util.dart';
import 'package:Raybank/src/utils/string_util.dart';

class TransferScreen extends StatefulWidget {

  const TransferScreen({Key key, this.balance}) : super(key: key);
  final double balance;
  @override
  _TransferScreenState createState() => _TransferScreenState();
}

class _TransferScreenState extends State<TransferScreen> {


  final _moneyMaskedController = createMoneyMaskedController();

  final _receiverController = TextEditingController();

  TransferController _transferController;


  @override
  void initState() {
    super.initState();
    _transferController = TransferController(_moneyMaskedController,
        _receiverController, widget.balance);
    MyActions.goToTransferPage = (){
      _transferController.goToSelectContactPage();
    };
  }

  @override
  Widget build(BuildContext context) {
    bool isErrorVisible = false;
    return PageCard(
      headerTitle: 'Qual o valor da transferência?',
      headerSubtitle: 'Você tem disponível ${convertToBRL(widget.balance)}',
      moneyMaskedController: _moneyMaskedController,
      errorText: 'Você não possui saldo suficiente na sua conta',
      isErrorVisible: (){
        isErrorVisible = _transferController.isAmountInvalid();
        return isErrorVisible;
      },
      inputChange: (value) {
        setState(() {
          isErrorVisible = _transferController.isAmountInvalid();
        });
      },
    );

  }
}
