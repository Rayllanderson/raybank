import 'package:flutter/material.dart';

class LoginButton extends StatelessWidget {

  final text;

  const LoginButton({Key key, this.text}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
        onPressed: () {},
        child: Text(text),
        style: ElevatedButton.styleFrom(
          minimumSize: Size(double.infinity, 40.0),
        )
    );
  }
}
