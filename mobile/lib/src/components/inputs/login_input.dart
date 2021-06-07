import 'package:flutter/material.dart';
import 'package:Raybank/src/themes/themes.dart';

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
        keyboardType: _isPassword ? TextInputType.number : TextInputType.text,
        style: TextStyle(color: Themes.textColor, fontSize: 17),
        maxLength: _isPassword ? 6 : null,
        decoration: InputDecoration(
          counterStyle: TextStyle(color: Themes.textColor),
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
