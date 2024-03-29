import 'package:Raybank/main.dart';
import 'package:Raybank/src/components/navigations/bottom_navigation.dart';
import 'package:Raybank/src/controllers/bank_account_controller.dart';
import 'package:Raybank/src/models/bank_account_model.dart';
import 'package:Raybank/src/themes/themes.dart';
import 'package:Raybank/src/utils/actions_util.dart';
import 'package:Raybank/src/views/home_subpages/deposit_screen.dart';
import 'package:Raybank/src/views/home_subpages/initial_screen.dart';
import 'package:Raybank/src/views/home_subpages/pay_screen.dart';
import 'package:Raybank/src/views/home_subpages/transfer_screen.dart';
import 'package:flutter/material.dart';


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

  floatingActionButton(index){
    bool isOnInitialPage = index == 0;
    bool isOnPaymentPage = index == 1;
    if (isOnInitialPage || isOnPaymentPage) return null;
    return Padding(
      padding: const EdgeInsets.only(bottom: 30, right: 10),
      child: FloatingActionButton(
        backgroundColor: Themes.primaryColor,
        child: Icon(Icons.arrow_forward),
        onPressed: (){
          if(index == 2){
            MyActions.goToTransferPage();
          }
          if(index == 3){
            MyActions.doADeposit();
          }
        },
      ),
    );
  }


  BankAccountController bankAccountController = new BankAccountController();
  BankAccountModel account = new BankAccountModel.empty();

  fetchData() async {
    account = await bankAccountController.fetchBankAccount();
    _onItemTapped(0);
  }

  refresh () async {
    var result = await bankAccountController.fetchBankAccount();
    setState(() {
      account = result;
    });
  }

  @override
  void initState() {
    fetchData();
    storage.setAccountName(account.userName);
    storage.setAccountName('${account.accountNumber}');
    super.initState();
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text('Raybank'),
          actions: [
            IconButton(icon: Icon(Icons.refresh_outlined),
                onPressed: refresh
            )],
        ),
        drawer: Drawer(
          child: FutureBuilder<BankAccountModel>(
              future: bankAccountController.fetchBankAccount(),
              builder: (context, snapshot) {
                return Column(
                  children: [
                    UserAccountsDrawerHeader(
                      currentAccountPicture: ClipOval(
                          child: Icon(Icons.account_circle_rounded, size: 58, color: Themes.textColor)
                      ),
                      accountName: Text(
                          account.userName ?? ''),
                      accountEmail: Text('Nº Conta ${account.accountNumber}'),
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
                );
              }),
        ),
        body: Stack(children: [
          Container(color: Themes.primaryColor),
          Center(
            child: SingleChildScrollView(
              child: [
                InitialScreen(accountModel: account,),
                PayScreen(account: account,),
                TransferScreen(balance: account.balance,),
                DepositScreen(),
              ].elementAt(_selectedIndex),
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
