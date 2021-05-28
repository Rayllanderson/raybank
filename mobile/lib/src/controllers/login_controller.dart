
import 'package:flutter/material.dart';

class LoginController {
  TextEditingController _usernameController;
  TextEditingController _passwordController;
  BuildContext context;

  LoginController(this._usernameController, this._passwordController, this.context);

  void login(){
    String username = this._usernameController.text;
    String password = this._passwordController.text;
    //mandar pra api aqui depois
    bool loginIsValid = username == 'ray' && password == '123';
    if(loginIsValid){
      print('login!');
      // Navigator.of(context).pushReplacementNamed('/home');
    }
  }

}
