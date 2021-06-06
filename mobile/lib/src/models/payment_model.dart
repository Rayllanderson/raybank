import 'package:mobile/src/models/enums/payment_type.dart';

class PaymentModel {
  PaymentType type;
  double availableAmount;
  double amount;


  PaymentModel(this.type, this.availableAmount);

  PaymentModel.fromJson(Map<String, dynamic> json) {
    amount = json['amount'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['amount'] = this.amount;
    return data;
  }

  getPaymentType(){
    switch(type){
      case PaymentType.BRAZILIAN_BOLETO:
        return "Boleto";
      case PaymentType.CREDIT_CARD:
        return "Cartão de crédito";
      case PaymentType.INVOICE:
        return "Fatura";
    }
  }

  getTitle(){
    switch(type){
      case PaymentType.BRAZILIAN_BOLETO:
        return "Saldo disponível";
      case PaymentType.CREDIT_CARD:
        return "Crédito disponível";
      case PaymentType.INVOICE:
        return "Valor da fatura";
    }
  }
}