import 'package:flutter/material.dart';
import 'package:mobile/src/models/login_model.dart';
import 'package:mobile/src/services/user_service.dart';

class LoginController {
  TextEditingController _usernameController;
  TextEditingController _passwordController;
  BuildContext context;

  final UserService userService = new UserService();

  LoginController(
      this._usernameController, this._passwordController, this.context);

  ///Valida os campos e realiza o login na aplicação.
  ///
  /// Caso login esteja correto, redireciona para [HomePage], senão, mostra um alert.
  void login() async {
    String username = this._usernameController.text;
    String password = this._passwordController.text;
    final model = LoginModel(username, password);
    userService.login(model);
  }
}
