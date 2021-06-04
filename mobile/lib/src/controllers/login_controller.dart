import 'package:flutter/material.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/components/alerts/alert.dart';
import 'package:mobile/src/exceptions/bad_request_exception.dart';
import 'package:mobile/src/exceptions/validation_exception.dart';
import 'package:mobile/src/models/login_model.dart';
import 'package:mobile/src/repositories/user_repository.dart';
import 'package:mobile/src/validations/login_validation.dart';

class LoginController {
  TextEditingController _usernameController;
  TextEditingController _passwordController;
  BuildContext context;

  LoginController(
      this._usernameController, this._passwordController, this.context);

  ///Valida os campos e realiza o login na aplicação.
  ///
  /// Caso login esteja correto, redireciona para [HomePage], senão, mostra um alert.
  void login() async {
    try {
      String username = this._usernameController.text;
      String password = this._passwordController.text;
      final model = LoginModel(username, password);
      validateLogin(model);
      await UserRepository.login(model);
        Navigator.of(navigatorKey.currentContext).pushReplacementNamed('/home');
    } on ValidationException catch (e) {
      Alert.displaySimpleAlert(e.title, e.message);
    } on BadRequestException catch (e){
      Alert.displaySimpleAlert(e.title, e.message);
    }
  }
}
