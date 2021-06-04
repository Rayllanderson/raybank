class BankAccountModel {
  int id;
  String userName;
  int accountNumber;
  double balance;
  CreditCardDto creditCardDto;

  BankAccountModel(
      {this.id,
        this.userName,
        this.accountNumber,
        this.balance,
        this.creditCardDto});

  BankAccountModel.empty(){
    id = 0;
    userName = '';
    accountNumber = 0;
    balance = 0;
    creditCardDto = CreditCardDto.empty();
  }

  BankAccountModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    userName = json['userName'];
    accountNumber = json['accountNumber'];
    balance = json['balance'];
    creditCardDto = json['creditCardDto'] != null
        ? new CreditCardDto.fromJson(json['creditCardDto'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['userName'] = this.userName;
    data['accountNumber'] = this.accountNumber;
    data['balance'] = this.balance;
    if (this.creditCardDto != null) {
      data['creditCardDto'] = this.creditCardDto.toJson();
    }
    return data;
  }
}

class CreditCardDto {
  int id;
  int cardNumber;
  double balance;
  double invoice;

  CreditCardDto({this.id, this.cardNumber, this.balance, this.invoice});

  CreditCardDto.empty(){
    this.id = 0;
    this.cardNumber = 0;
    this.balance = 0;
    this.invoice = 0;
  }

  CreditCardDto.fromJson(Map<String, dynamic> json) {
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