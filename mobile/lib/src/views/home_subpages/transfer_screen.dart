import 'package:flutter/material.dart';
import 'package:flutter_masked_text/flutter_masked_text.dart';
import 'package:mobile/src/components/cards/page_card.dart';
import 'package:mobile/src/controllers/transfer_controller.dart';

class TransferScreen extends StatefulWidget {

  const TransferScreen({Key key}) : super(key: key);

  @override
  _TransferScreenState createState() => _TransferScreenState();
}

class _TransferScreenState extends State<TransferScreen> {


  final _moneyMaskedController = MoneyMaskedTextController(
      decimalSeparator: ',',
      thousandSeparator: '.',
      leftSymbol: 'R\$ '
  );

  final _receiverController = TextEditingController();
  final _errorController = TextEditingController();

  TransferController transferController;

  @override
  void initState() {
    super.initState();
    transferController = TransferController(_moneyMaskedController,
        _receiverController);
  }

  @override
  Widget build(BuildContext context) {
    bool isErrorVisible = false;
    return PageCard(
      headerTitle: 'Qual o valor da transferência?',
      headerSubtitle: 'Você tem disponível R\$ 140',
      moneyMaskedController: _moneyMaskedController,
      errorText: 'O valor é maior que o saldo disponível em sua conta',
      isErrorVisible: (){
        isErrorVisible = transferController.isAmountInvalid();
        return isErrorVisible;
      },
      inputChange: (value) {
        setState(() {
          isErrorVisible = transferController.isAmountInvalid();
        });
      },
    );

  }
}
