import 'package:intl/intl.dart';

///recorta os campos em brancos da string e verifica se estão vazios.
///
///Return [true] caso o campo esteja vazio. Caso não esteja, return [false].
bool isEmpty(String field){
  return field.trim().isEmpty;
}

String convertToBRL(double amount){
  double value = amount;
  if (amount < 0){
    value = amount * -1;
  }
  final formatter = new NumberFormat.simpleCurrency(decimalDigits: 2, locale: "pt_BR");
  return formatter.format(value);
}