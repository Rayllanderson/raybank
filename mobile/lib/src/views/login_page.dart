import 'package:flutter/material.dart';
import 'package:mobile/src/components/buttons/login_button.dart';
import 'package:mobile/src/components/inputs/login_input.dart';
import 'package:mobile/src/components/logos/login_logo.dart';
import 'package:mobile/src/controllers/login_controller.dart';
import 'package:mobile/src/themes/themes.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({Key key}) : super(key: key);

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final usernameController = TextEditingController();
  final passwordController = TextEditingController();
  LoginController _loginController;

  @override
  void initState() {
    super.initState();
    _loginController =
        LoginController(usernameController, passwordController, context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Stack(children: [
      Container(color: Themes.primaryColor),
      Center(
        child: SingleChildScrollView(
          child: Column(
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
                        child: LoginInput('Username', false, usernameController),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: LoginInput('Password', true, passwordController),
                      ),
                      SizedBox(
                        height: 20,
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: LoginButton(
                          text: 'LOGIN',
                          onPress: () {
                            _loginController.login();
                          },
                        ),
                      ),
                      SizedBox(
                        height: 20,
                      ),
                      TextButton(
                        child: RichText(
                          text: TextSpan(
                            children: [
                              TextSpan(
                                  text: "Criar Conta ",
                                  style: TextStyle(fontSize: 18)),
                              WidgetSpan(
                                child:
                                    Icon(Icons.arrow_forward_rounded, size: 18),
                              ),
                            ],
                          ),
                        ),
                        style: TextButton.styleFrom(
                          primary: Themes.textColor,
                          textStyle: const TextStyle(fontSize: 18),
                        ),
                        onPressed: () {
                          Navigator.of(context).pushNamed('/register');
                        },
                      )
                    ],
                  ),
                ),
              ]),
        ),
      ),
    ]));
  }
}
