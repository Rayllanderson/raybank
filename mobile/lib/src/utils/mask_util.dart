
String unmaskMoney(String maskedMoney){
  String unmaskedMoney = maskedMoney.replaceAll('R\$', '');
  unmaskedMoney = unmaskedMoney.replaceAll('.', '');
  return unmaskedMoney.replaceAll(',', '.');
}