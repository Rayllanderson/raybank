import 'package:flutter/material.dart';
import 'package:mobile/src/components/navigations/bottom_navigation.dart';
import 'package:mobile/src/themes/themes.dart';
import 'package:mobile/src/utils/actions_util.dart';
import 'package:mobile/src/views/home_subpages/deposit_screen.dart';
import 'package:mobile/src/views/home_subpages/initial_screen.dart';
import 'package:mobile/src/views/home_subpages/pay_screen.dart';
import 'package:mobile/src/views/home_subpages/transfer_screen.dart';

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
    PayScreen(),
    TransferScreen(),
    DepositScreen(),
    DepositScreen()
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
            MyActions.goToTransferPage();
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
                accountEmail: null,
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
