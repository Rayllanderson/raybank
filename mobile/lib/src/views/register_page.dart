import 'package:flutter/material.dart';
import 'package:mobile/src/components/buttons/login_button.dart';
import 'package:mobile/src/components/inputs/login_input.dart';
import 'package:mobile/src/components/logos/login_logo.dart';
import 'package:mobile/src/themes/util.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({Key key}) : super(key: key);

  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
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
            Container(
              height: 35,
            ),
            Padding(
              padding: const EdgeInsets.all(10.0),
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: LoginInput('Nome', false),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: LoginInput('Username', false),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: LoginInput('Password', true),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: LoginButton(text: 'REGISTRAR'),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  TextButton(
                    child: RichText(
                      text: TextSpan(
                        children: [
                          WidgetSpan(
                            child: Icon(Icons.arrow_back, size: 18),
                          ),
                          TextSpan(
                              text: "Voltar para o Login",
                              style: TextStyle(fontSize: 18)
                          ),
                        ],
                      ),
                    ),
                    style: TextButton.styleFrom(
                      primary: Themes.textColor,
                      textStyle: const TextStyle(fontSize: 18),
                    ),
                    onPressed: (){
                      Navigator.of(context).pushNamed('/');
                    },
                  )
                ],
              ),
            ),
          ]),
    ]));
  }
}
