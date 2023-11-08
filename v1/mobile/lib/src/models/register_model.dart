import 'package:Raybank/src/models/login_model.dart';

class RegisterModel {
  String name;
  String username;
  String password;

  RegisterModel({this.name, this.username, this.password});

  RegisterModel.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    username = json['username'];
    password = json['password'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = this.name;
    data['username'] = this.username;
    data['password'] = this.password;
    return data;
  }

  toLoginModel() {
    return LoginModel(username, password);
  }
}
