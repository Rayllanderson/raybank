import 'package:flutter/material.dart';
import 'package:mobile/src/themes/themes.dart';

class PrimaryButton extends StatelessWidget {
  final text;
  final void Function() onPress;

  const PrimaryButton({Key key, this.text, this.onPress}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
        onPressed: onPress,
        child: Text(
          text,
          style: TextStyle(color: Themes.textColor, fontSize: 18),
        ),
        style: ElevatedButton.styleFrom(
          primary: Themes.primaryColor,
          minimumSize: Size(double.infinity, 43.0),
        ));
  }
}
