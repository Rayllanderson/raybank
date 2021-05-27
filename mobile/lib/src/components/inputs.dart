import 'package:flutter/material.dart';

class LoginInput extends StatelessWidget {

  final _labelText;
  final _isPassword;

  LoginInput(this._labelText, this._isPassword);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: TextField(
        obscureText: _isPassword,
        decoration: InputDecoration(
            border: OutlineInputBorder(),
            labelText: this._labelText
        ),
      ),
    );
  }
}
