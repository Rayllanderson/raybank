class StatementModel {
  int id;
  String to;
  String message;
  String moment;
  String statementType;
  int amount;
  String identificationType;

  StatementModel(
      {this.id,
        this.to,
        this.message,
        this.moment,
        this.statementType,
        this.amount,
        this.identificationType});

  StatementModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    to = json['to'];
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
    data['message'] = this.message;
    data['moment'] = this.moment;
    data['statementType'] = this.statementType;
    data['amount'] = this.amount;
    data['identificationType'] = this.identificationType;
    return data;
  }
}