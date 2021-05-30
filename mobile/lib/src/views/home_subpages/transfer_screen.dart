import 'package:flutter/material.dart';
import 'package:mobile/src/components/cards/page_card.dart';
import 'package:mobile/src/controllers/transfer_controller.dart';
import 'package:mobile/src/utils/creator_util.dart';
import 'package:mobile/src/views/home_page.dart';

class TransferScreen extends StatefulWidget {

  const TransferScreen({Key key}) : super(key: key);

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
        _receiverController);
    MyActions.goToTransferPage = (){
      _transferController.goToSelectContactPage();
    };
  }

  @override
  Widget build(BuildContext context) {
    bool isErrorVisible = false;
    return PageCard(
      headerTitle: 'Qual o valor da transferência?',
      headerSubtitle: 'Você tem disponível R\$ 140',
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
