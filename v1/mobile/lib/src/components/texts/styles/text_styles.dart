import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MyTextStyle{

  /// fontSize: 28,
  ///
  /// fontWeight: FontWeight.bold
  static title(){
    return TextStyle(
      fontSize: 28,
      fontWeight: FontWeight.bold
    );
  }

  static listTitle(){
    return TextStyle(
        fontSize: 20,
    );
  }

  static listSubtitle(){
    return TextStyle(
      fontSize: 18,
    );
  }


  static subtitle(){
    return TextStyle(
        fontSize: 18,
        fontWeight: FontWeight.normal
    );
  }

  ///FontWeight.Bold
  static fwBold() {
    return TextStyle(
        fontWeight: FontWeight.bold
    );
  }

  ///FontWeight.w300
  static fw300() {
    return TextStyle(
        fontWeight: FontWeight.w300
    );
  }

  ///FontWeight.w300
  static fw400() {
    return TextStyle(
        fontWeight: FontWeight.w400
    );
  }

  static errorText(){
    return TextStyle(
        fontSize: 18,
        color: Colors.red,
    );
  }
}