import 'package:flutter/material.dart';
import 'package:mobile/src/themes/util.dart';

class LoginInput extends StatelessWidget {
  final _labelText;
  final _isPassword;
  final controller;

  LoginInput(this._labelText, this._isPassword, this.controller);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: TextField(
        controller: controller,
        obscureText: _isPassword,
        style: TextStyle(color: Themes.textColor),
        decoration: InputDecoration(
          hintText: _labelText,
          hintStyle: TextStyle(color: Themes.textColor),
          enabledBorder:  UnderlineInputBorder(
            borderSide: BorderSide(color: Themes.primaryColor),
          ),
          focusedBorder: UnderlineInputBorder(
            borderSide: BorderSide(color: Themes.textColor, width: 1),
          ),
        ),

      ),
    );
  }
}
