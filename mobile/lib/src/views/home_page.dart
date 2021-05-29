import 'package:flutter/material.dart';
import 'package:mobile/src/components/navigations/bottom_navigation.dart';
import 'package:mobile/src/themes/themes.dart';
import 'package:mobile/src/views/inital_screen.dart';
import 'package:mobile/src/views/transfer_screen.dart';

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
    TransferScreen(),
    Text(
      'Tela de depósito',
    ),
  ];

  floatingActionButton(index){
    bool isOnInitialPage = index == 0;
    if (isOnInitialPage) return null;
    return Padding(
      padding: const EdgeInsets.only(bottom: 30, right: 10),
      child: FloatingActionButton(
        backgroundColor: Themes.primaryColor,
        child: Icon(Icons.arrow_forward),
        onPressed: (){
          if(index == 1){
            print('indo pra página de transferência');
          }
          if(index == 2){
            print('indo pra página de depósito');
          }
        },
      ),
    );
  }


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
        floatingActionButton: floatingActionButton(_selectedIndex),
        bottomNavigationBar: BottomNavigator(
          selectedIndex: _selectedIndex,
          onItemTapped: (index) {
            _onItemTapped(index);
          },
        ));
  }
}
