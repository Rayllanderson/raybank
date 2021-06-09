import 'package:flutter/material.dart';
import 'package:Raybank/src/components/buttons/login_button.dart';
import 'package:Raybank/src/components/inputs/login_input.dart';
import 'package:Raybank/src/components/logos/login_logo.dart';
import 'package:Raybank/src/controllers/register_controller.dart';
import 'package:Raybank/src/themes/themes.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({Key key}) : super(key: key);

  @override
  _RegisterPageState createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final nameController = TextEditingController();
  final usernameController = TextEditingController();
  final passwordController = TextEditingController();
  RegisterController _registerController;

  @override
  void initState() {
    super.initState();
    _registerController = RegisterController(
        nameController, usernameController, passwordController, context);
  }

  @override
  void dispose() {
    nameController.clear();
    nameController.dispose();
    usernameController.dispose();
    usernameController.clear();
    passwordController.dispose();
    passwordController.clear();
    super.dispose();
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
                            child: LoginInput('Nome', false, nameController),
                          ),
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
                              text: 'REGISTRAR',
                              onPress: () {
                                _registerController.register();
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
                                  WidgetSpan(
                                    child: Icon(Icons.arrow_back, size: 18),
                                  ),
                                  TextSpan(
                                      text: "Voltar para o Login",
                                      style: TextStyle(fontSize: 18)),
                                ],
                              ),
                            ),
                            style: TextButton.styleFrom(
                              primary: Themes.textColor,
                              textStyle: const TextStyle(fontSize: 18),
                            ),
                            onPressed: () {
                              Navigator.of(context).pushNamed('/');
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
