import 'package:flutter_masked_text/flutter_masked_text.dart';

MoneyMaskedTextController createMoneyMaskedController(){
  return MoneyMaskedTextController(
      decimalSeparator: ',',
      thousandSeparator: '.',
      leftSymbol: 'R\$ '
  );
}