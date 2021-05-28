import 'package:flutter/material.dart';
import 'package:mobile/src/themes/themes.dart';

class LoginLogo extends StatelessWidget {
  const LoginLogo({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(25),
      child: Container(
        width: 200,
        height: 200,
        color: Themes.secondaryColor,
        child: Icon(
          Icons.account_balance_sharp,
          size: 60,
          color: Themes.primaryColor,),
      ),
    );
  }
}
