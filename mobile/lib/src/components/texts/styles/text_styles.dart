import 'package:flutter/cupertino.dart';

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
}