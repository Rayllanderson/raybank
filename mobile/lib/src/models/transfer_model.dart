class TransferModel {
  String to;
  double amount;
  String message;

  TransferModel({this.to, this.amount, this.message});

  TransferModel.empty({this.to, this.amount, this.message}){
    amount = 0;
    to = '';
    message = null;
  }

  TransferModel.fromJson(Map<String, dynamic> json) {
    to = json['to'];
    amount = json['amount'];
    message = json['message'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['to'] = this.to;
    data['amount'] = this.amount;
    data['message'] = this.message;
    return data;
  }
}