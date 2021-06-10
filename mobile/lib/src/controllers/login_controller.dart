import 'package:Raybank/src/models/enums/request.dart';
import 'package:flutter/material.dart';
import 'package:Raybank/src/models/login_model.dart';
import 'package:Raybank/src/services/user_service.dart';

class LoginController {
  TextEditingController _usernameController;
  TextEditingController _passwordController;

  final UserService userService = new UserService();
  RequestState state = RequestState.NONE;

  LoginController(this._usernameController, this._passwordController);

  ///Valida os campos e realiza o login na aplicação.
  ///
  /// Caso login esteja correto, redireciona para [HomePage], senão, mostra um alert.
  Future<RequestState> login() async {
    state = RequestState.LOADING;
    String username = this._usernameController.text;
    String password = this._passwordController.text;
    final model = LoginModel(username, password);
    return await userService.login(model);
  }
}
