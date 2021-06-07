import 'package:flutter/material.dart';
import 'package:Raybank/main.dart';

class MyDrawer extends StatelessWidget {
  const MyDrawer({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Drawer(
        child: Column(
          children: [
            UserAccountsDrawerHeader(accountName: null, accountEmail: null,
            ),
            ListTile(
              leading: Icon(Icons.home),
              title: Text('Inicio'),
              subtitle: Text('Tela de início'),
              onTap: () {
                Navigator.of(navigatorKey.currentContext).pushReplacementNamed('/home');
              },
            ),
            ListTile(
              leading: Icon(Icons.logout),
              title: Text('Logout'),
              subtitle: Text('Sair do aplicativo'),
              onTap: () {
                storage.setToken('');
                Navigator.of(context).pushReplacementNamed('/');
              },
            )
          ],
        )
    );
  }
}
