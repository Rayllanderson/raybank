import 'package:flutter/material.dart';
import 'package:mobile/src/components/cards/page_card.dart';
import 'package:mobile/src/controllers/transfer_controller.dart';
import 'package:mobile/src/utils/creator_util.dart';

class PayScreen extends StatefulWidget {

  const PayScreen({Key key}) : super(key: key);

  @override
  _PayScreenState createState() => _PayScreenState();
}

class _PayScreenState extends State<PayScreen> {


  final _moneyMaskedController = createMoneyMaskedController();

  final _receiverController = TextEditingController();

  TransferController _transferController;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    bool isErrorVisible = false;
    return PageCard(
      headerTitle: 'Qual o valor do pagamento?',
      headerSubtitle: '',
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
