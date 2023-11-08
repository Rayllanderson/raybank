class ContactModel {
  int id;
  String username;
  String name;
  int accountNumber;

  ContactModel({this.id, this.username, this.name, this.accountNumber});

  ContactModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    username = json['username'];
    name = json['name'];
    accountNumber = json['accountNumber'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['username'] = this.username;
    data['name'] = this.name;
    data['accountNumber'] = this.accountNumber;
    return data;
  }
}