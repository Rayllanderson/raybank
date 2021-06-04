import 'package:flutter/material.dart';
import 'package:mobile/src/components/alerts/alert.dart';
import 'package:mobile/src/exceptions/bad_request_exception.dart';
import 'package:mobile/src/exceptions/validation_exception.dart';
import 'package:mobile/src/models/register_model.dart';
import 'package:mobile/src/services/user_service.dart';
import 'package:mobile/src/validations/register_validation.dart';
import 'package:mobile/src/repositories/user_repository.dart';

class RegisterController {
  TextEditingController _nameController;
  TextEditingController _usernameController;
  TextEditingController _passwordController;
  BuildContext context;

  RegisterController(this._nameController, this._usernameController,
      this._passwordController, this.context);

  final UserService userService = new UserService();

  ///Valida os campos e realiza o cadastro na aplicação.
  ///
  /// Caso o cadastro seja realizado com sucesso, redireciona para [LoginPage], senão, mostra um alert.
  void register() async {
    String name = this._nameController.text;
    String username = this._usernameController.text;
    String password = this._passwordController.text;
    final model = RegisterModel(name: name, username: username, password: password);
    userService.register(model);
  }
}
