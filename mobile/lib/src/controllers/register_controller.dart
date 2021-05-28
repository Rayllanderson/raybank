import 'package:flutter/material.dart';
import 'package:mobile/src/components/alerts/alert.dart';
import 'package:mobile/src/exceptions/validation_exception.dart';
import 'package:mobile/src/models/register_model.dart';
import 'package:mobile/src/validations/register_validation.dart';

class RegisterController {
  TextEditingController _nameController;
  TextEditingController _usernameController;
  TextEditingController _passwordController;
  BuildContext context;

  RegisterController(this._nameController, this._usernameController, this._passwordController, this.context);

  ///Valida os campos e realiza o cadastro na aplicação.
  ///
  /// Caso o cadastro seja realizado com sucesso, redireciona para [LoginPage], senão, mostra um alert.
  void register() {
    try {
      String name = this._nameController.text;
      String username = this._usernameController.text;
      String password = this._passwordController.text;
      final model = RegisterModel(name, username, password);
      validateRegister(model);
      if (true) {
        Navigator.of(context).pushReplacementNamed('/');
        Alert.displaySimpleAlert('Sucesso', 'Cadastro realizado com sucesso!');
      }
    } on ValidationException catch (e) {
      Alert.displaySimpleAlert(e.title, e.message);
    }
  }
}
