import 'package:flutter/material.dart';
import 'package:mobile/src/components/list_views/statement_list_view.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';
import 'package:mobile/src/controllers/bank_account_controller.dart';
import 'package:mobile/src/models/bank_account_model.dart';
import 'package:mobile/src/models/statement_model.dart';
import 'package:mobile/src/themes/themes.dart';
import 'package:mobile/src/utils/string_util.dart';

class BalanceScreen extends StatefulWidget {
  const BalanceScreen({Key key}) : super(key: key);

  @override
  _BalanceScreenState createState() => _BalanceScreenState();
}

class _BalanceScreenState extends State<BalanceScreen> {
  BankAccountController bankAccountController = new BankAccountController();
  BankAccountModel account = new BankAccountModel.empty();
  List<StatementModel> statements = List.empty();

  fetchData() async {
    account = await bankAccountController.fetchBankAccount();
    statements = await bankAccountController.fetchStatements();
  }

  @override
  void initState() {
    fetchData();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Stack(
          children:[
        Container(color: Themes.primaryColor),
        SingleChildScrollView(
          child: Column(
            children: [
              Padding(
                padding: const EdgeInsets.only(left: 15, top: 8, bottom: 8),
                child: FutureBuilder(
                    future: bankAccountController.fetchBankAccount(),
                    builder: (context, snapshot) {
                      return Container(
                        alignment: Alignment.center,
                        child: Text(
                          'Olá, ${account.userName}!',
                          style: TextStyle(
                              fontWeight: FontWeight.bold,
                              color: Themes.textColor,
                              fontSize: 28),
                        ),
                      );
                    }),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Container(
                  width: double.infinity,
                  height: 110,
                  child: Card(
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Padding(
                      padding: const EdgeInsets.only(left: 15, top: 8, bottom: 8),
                      child: FutureBuilder(
                        future: bankAccountController.fetchBankAccount(),
                        builder: (context, snapshot) {
                        return Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text('Saldo na conta', style: MyTextStyle.subtitle()),
                            SizedBox(height: 10,),
                            Text(convertToBRL(account.balance), style: MyTextStyle.title(),)
                          ]
                        );},
                      ),
                    ),
                  ),
                ),
              ),
              SizedBox(height: 5,),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Container(
                  width: double.infinity,
                  height: 500,
                  child: Card(
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Padding(
                      padding: const EdgeInsets.only(left: 15, top: 8, bottom: 8),
                      child: SizedBox(
                        height: 100,
                        child: FutureBuilder(
                          future: bankAccountController.fetchStatements(),
                          builder: (context, snapshot) {
                          return Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Histórico', style: MyTextStyle.subtitle()),
                                SizedBox(height: 10,),
                                Expanded(child: StatementList(statements: List.from(statements.reversed),)),
                              ]
                            );
                          }
                  ),
                        ),
                      ),
                    ),
                  ),
                ),
            ],
          ),
        ),
      ]
      ),
    );
  }
}
