import 'package:flutter/material.dart';
import 'package:mobile/src/components/buttons/login_button.dart';
import 'package:mobile/src/components/inputs/login_input.dart';
import 'package:mobile/src/components/logos/login_logo.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({Key key}) : super(key: key);

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Stack(children: [
      Container(color: Colors.purpleAccent),
      Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: LoginLogo(),
            ),
            Container(height: 35,),
            Padding(
              padding: const EdgeInsets.all(10.0),
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: LoginInput('Username', false),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: LoginInput('Password', true),
                  ),
                  SizedBox(height: 20,),
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: LoginButton(text: 'LOGIN'),
                  ),
                ],
              ),
            ),
          ]),
    ]));
  }
}
