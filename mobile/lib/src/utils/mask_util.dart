import 'package:flutter_masked_text/flutter_masked_text.dart';

final moneyMaskedController = MoneyMaskedTextController(
    decimalSeparator: ',',
    thousandSeparator: '.',
    leftSymbol: 'R\$ '
);

String unmaskMoney(String maskedMoney){
  String unmaskedMoney = maskedMoney.replaceAll('R\$', '');
  unmaskedMoney = unmaskedMoney.replaceAll('.', '');
  return unmaskedMoney.replaceAll(',', '.');
}