import 'package:mobile/src/models/login_model.dart';

class RegisterModel extends LoginModel{
  String name;

  RegisterModel(this.name, String username, String password) : super(username, password);

  toLoginModel(){
    return LoginModel(username, password);
  }
}