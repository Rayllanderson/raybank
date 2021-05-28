import 'package:flutter/material.dart';
import 'package:mobile/src/themes/util.dart';

class LoginButton extends StatelessWidget {
  final text;
  final void Function() onPress;

  const LoginButton({Key key, this.text, this.onPress}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
        onPressed: onPress,
        child: Text(
          text,
          style: TextStyle(color: Themes.primaryColor, fontSize: 18),
        ),
        style: ElevatedButton.styleFrom(
          primary: Themes.secondaryColor.withAlpha(240),
          minimumSize: Size(double.infinity, 43.0),
        ));
  }
}
