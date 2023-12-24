class CreditCardModel {
  int id;
  int cardNumber;
  int balance;
  int invoice;

  CreditCardModel({this.id, this.cardNumber, this.balance, this.invoice});

  CreditCardModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    cardNumber = json['cardNumber'];
    balance = json['balance'];
    invoice = json['invoice'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['cardNumber'] = this.cardNumber;
    data['balance'] = this.balance;
    data['invoice'] = this.invoice;
    return data;
  }
}