class TransferModel {
  String to;
  String toName;
  double amount;
  String message;

  TransferModel({this.to, this.amount, this.message});

  TransferModel.empty({this.to, this.amount, this.message}){
    amount = 0;
    to = '';
    toName = '';
    message = null;
  }

  TransferModel.fromJson(Map<String, dynamic> json) {
    to = json['to'];
    toName = json['toName'];
    amount = json['amount'];
    message = json['message'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['to'] = this.to;
    data['toName'] = this.toName;
    data['amount'] = this.amount;
    data['message'] = this.message;
    return data;
  }
}