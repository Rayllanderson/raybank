import 'package:flutter/material.dart';
import 'package:mobile/src/components/list_views/statement_list_view.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';
import 'package:mobile/src/controllers/bank_account_controller.dart';
import 'package:mobile/src/models/bank_account_model.dart';
import 'package:mobile/src/models/statement_model.dart';
import 'package:mobile/src/themes/themes.dart';
import 'package:mobile/src/utils/string_util.dart';

class BalanceScreen extends StatefulWidget {
  const BalanceScreen({Key key, this.account}) : super(key: key);

  final BankAccountModel account;

  @override
  _BalanceScreenState createState() => _BalanceScreenState();
}

class _BalanceScreenState extends State<BalanceScreen> {

  fetchData() async {
    statements = await bankAccountController.fetchStatements();
  }

  BankAccountController bankAccountController = new BankAccountController();
  List<StatementModel> statements = List.empty();

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
                child: Container(
                        alignment: Alignment.center,
                        child: Text(
                          'Olá, ${widget.account.userName}!',
                          style: TextStyle(
                              fontWeight: FontWeight.bold,
                              color: Themes.textColor,
                              fontSize: 28),
                        ),
                    ),
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
                      child: FutureBuilder<BankAccountModel>(
                        future: bankAccountController.fetchBankAccount(),
                        builder: (context, snapshot) {
                        return Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text('Saldo na conta', style: MyTextStyle.subtitle()),
                            SizedBox(height: 10,),
                            Text(convertToBRL(widget.account.balance), style: MyTextStyle.title(),)
                          ]
                        );
                        },
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
                        child: FutureBuilder<List<StatementModel>>(
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
