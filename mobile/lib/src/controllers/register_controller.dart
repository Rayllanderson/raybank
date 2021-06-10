import 'package:Raybank/src/models/enums/request.dart';
import 'package:Raybank/src/models/register_model.dart';
import 'package:Raybank/src/services/user_service.dart';
import 'package:flutter/material.dart';

class RegisterController {
  TextEditingController _nameController;
  TextEditingController _usernameController;
  TextEditingController _passwordController;

  RegisterController(this._nameController, this._usernameController, this._passwordController);

  final UserService userService = new UserService();

  ///Valida os campos e realiza o cadastro na aplicação.
  ///
  /// Caso o cadastro seja realizado com sucesso, redireciona para [LoginPage], senão, mostra um alert.
  Future<RequestState> register() async {
    String name = this._nameController.text;
    String username = this._usernameController.text;
    String password = this._passwordController.text;
    final model = RegisterModel(name: name, username: username, password: password);
    return userService.register(model);
  }
}
