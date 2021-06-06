import 'package:flutter/material.dart';
import 'package:flutter_masked_text/flutter_masked_text.dart';
import 'package:mobile/src/components/cards/page_card.dart';

class DepositScreen extends StatefulWidget {
  const DepositScreen({Key key}) : super(key: key);

  @override
  _DepositScreenState createState() => _DepositScreenState();
}

class _DepositScreenState extends State<DepositScreen> {

  final moneyMaskedController = MoneyMaskedTextController(
      decimalSeparator: ',',
      thousandSeparator: '.',
      leftSymbol: 'R\$ '
  );

  @override
  Widget build(BuildContext context) {
    return PageCard(
      headerTitle: 'Quanto quer depositar?',
      headerSubtitle: '',
      moneyMaskedController: moneyMaskedController,
      errorText: '',
      isErrorVisible: (){return false;},
      inputChange: (value) {
        print(value);
      },
    );
  }
}
