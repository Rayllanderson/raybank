import 'package:flutter/material.dart';
import 'package:mobile/src/components/buttons/login_button.dart';
import 'package:mobile/src/components/inputs/login_input.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({Key key}) : super(key: key);

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: LoginInput('Username', false),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: LoginInput('Password', true),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: LoginButton(text: 'LOGIN'),
          ),
        ]));
  }
}
