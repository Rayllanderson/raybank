import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:mobile/src/utils/string_util.dart';

class StatementModel {
  int id;
  String to;
  String from;
  String message;
  String moment;
  String statementType;
  double amount;
  String identificationType;

  StatementModel({this.id,
    this.to,
    this.message,
    this.moment,
    this.statementType,
    this.amount,
    this.identificationType});

  StatementModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    to = json['to'];
    from = json['from'];
    message = json['message'];
    moment = json['moment'];
    statementType = json['statementType'];
    amount = json['amount'];
    identificationType = json['identificationType'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['to'] = this.to;
    data['from'] = this.from;
    data['message'] = this.message;
    data['moment'] = this.moment;
    data['statementType'] = this.statementType;
    data['amount'] = this.amount;
    data['identificationType'] = this.identificationType;
    return data;
  }

  String getMoment() {
    return DateFormat("MMMM d", 'pt_BR').format(DateTime.parse(moment).toLocal());
  }

  bool hasMoneyCameIn() {
    return statementType == 'DEPOSIT' ||
        (identificationType != null && identificationType == 'RECEIVER');
  }

  getIcon() {
    return Icons.attach_money;
  }

  getIconColor() {
    return hasMoneyCameIn() ? Colors.green : Colors.red;
  }

  getTitle() {
    if (statementType == 'TRANSFER') {
      return hasMoneyCameIn()
          ? 'Transferência recebida'
          : 'Transferência enviada';
    }
    if (statementType == 'DEPOSIT'){
      return 'Deposito recebido';
    }
    if (statementType == 'BRAZILIAN_BOLETO'){
      return 'Pagamento via boleto efetuado';
    }
    if (statementType == 'INVOICE_PAYMENT') {
      return 'Pagamento da fatura';
    }
    if (statementType == 'CREDIT_CARD_PAYMENT') {
      return 'Pagamento com cartão de crédito';
    }
  }

  getSubtitle() {
    if (statementType == 'TRANSFER') {
      return hasMoneyCameIn()
          ? '${convertToBRL(amount)} de $from'
          : '${convertToBRL(amount)} para $to';
    }
    return '${convertToBRL(amount)}';
  }

  getMessage(){
    String message = 'Valor: ${getSubtitle()}\n';
    String day = DateFormat("d", 'pt_BR').format(DateTime.parse(moment).toUtc());
    String month = DateFormat("MMMM", 'pt_BR').format(DateTime.parse(moment).toUtc());
    String hour = DateFormat("jm", 'pt_BR').format(DateTime.parse(moment).toUtc());
    message += 'Data: $day de $month, $hour\n';
    if (this.message != null && this.message.isNotEmpty){
      message += "Mensagem: ${this.message}\n";
    }
    return message;
  }
}