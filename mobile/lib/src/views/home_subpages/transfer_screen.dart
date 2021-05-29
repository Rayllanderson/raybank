import 'package:flutter/material.dart';
import 'package:flutter_masked_text/flutter_masked_text.dart';
import 'package:mobile/src/components/cards/page_card.dart';
import 'package:mobile/src/utils/mask_util.dart';

class TransferScreen extends StatefulWidget {
  const TransferScreen({Key key}) : super(key: key);

  @override
  _TransferScreenState createState() => _TransferScreenState();
}

class _TransferScreenState extends State<TransferScreen> {

  final moneyMaskedController = MoneyMaskedTextController(
      decimalSeparator: ',',
      thousandSeparator: '.',
      leftSymbol: 'R\$ '
  );

  @override
  Widget build(BuildContext context) {
    return PageCard(
      headerTitle: 'Qual o valor da transferência?',
      headerSubtitle: 'Você tem disponível R\$ 140',
      moneyMaskedController: moneyMaskedController,
      inputChange: (value) {
        print(value);
      },
    );

  }
}
