import 'package:flutter/material.dart';
import 'package:mobile/src/components/alerts/alert.dart';
import 'package:mobile/src/exceptions/validation_exception.dart';
import 'package:mobile/src/validations/login_validation.dart';
import 'package:mobile/src/validations/register_validation.dart';

class RegisterController {
  TextEditingController _nameController;
  TextEditingController _usernameController;
  TextEditingController _passwordController;
  BuildContext context;

  RegisterController(this._nameController, this._usernameController, this._passwordController, this.context);

  void register() {
    try {
      String name = this._nameController.text;
      String username = this._usernameController.text;
      String password = this._passwordController.text;
      validateRegister(name, username, password);
      //mandar pra api aqui depois
      bool success = true;
      if (success) {
        Navigator.of(context).pushReplacementNamed('/');
        Alert.displaySimpleAlert('Sucesso', 'Cadastro realizado com sucesso!');
      } else {
        Alert.displaySimpleAlert('Erro', 'Login ou senha estão incorretos.');
      }
    } on ValidationException catch (e) {
      Alert.displaySimpleAlert(e.title, e.message);
    }
  }
}
