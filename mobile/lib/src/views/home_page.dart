import 'package:flutter/material.dart';
import 'package:mobile/src/components/navigations/bottom_navigation.dart';
import 'package:mobile/src/themes/themes.dart';
import 'package:mobile/src/views/inital_screen.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _selectedIndex = 0;

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  static const List<Widget> _widgetOptions = <Widget>[
    InitialScreen(),
    Text(
      'Tela de transferência',
    ),
    Text(
      'Tela de depósito',
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Raybank'),
        ),
        drawer: Drawer(
          child: Column(
            children: [
              UserAccountsDrawerHeader(
                  currentAccountPicture: ClipOval(
                      child: Image.network('https://avatars.githubusercontent.com/u/63964369?v=4')
                  ),
                  accountName: Text('Rayllanderson'),
                  accountEmail: Text('ray@gmail.com')
              ),
              ListTile(
                leading: Icon(Icons.home),
                title: Text('Inicio'),
                subtitle: Text('Tela de início'),
                onTap: () {
                  Navigator.of(context).pushReplacementNamed('/home');
                },
              ),
              ListTile(
                leading: Icon(Icons.logout),
                title: Text('Logout'),
                subtitle: Text('Sair do aplicativo'),
                onTap: () {
                  Navigator.of(context).pushReplacementNamed('/');
                },
              )
            ],
          ),
        ),
        body: Stack(children: [
          Container(color: Themes.primaryColor),
          Center(
            child: SingleChildScrollView(
              child: _widgetOptions.elementAt(_selectedIndex),
            ),
          ),
        ]),
        bottomNavigationBar: BottomNavigator(
          selectedIndex: _selectedIndex,
          onItemTapped: (index) {
            _onItemTapped(index);
          },
        ));
  }
}
