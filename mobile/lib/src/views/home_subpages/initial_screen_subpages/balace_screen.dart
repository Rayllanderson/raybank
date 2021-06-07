import 'package:flutter/material.dart';
import 'package:Raybank/src/components/cards/statement_card.dart';
import 'package:Raybank/src/components/texts/styles/text_styles.dart';
import 'package:Raybank/src/controllers/bank_account_controller.dart';
import 'package:Raybank/src/models/bank_account_model.dart';
import 'package:Raybank/src/models/statement_model.dart';
import 'package:Raybank/src/themes/themes.dart';
import 'package:Raybank/src/utils/string_util.dart';

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
      appBar: AppBar(
        actions: [
          IconButton(icon: Icon(Icons.refresh_outlined),
              onPressed: fetchData
          )],
      ),
      body: Stack(children: [
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
                        padding:
                            const EdgeInsets.only(left: 15, top: 15, bottom: 5),
                        child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text('Saldo na conta',
                                  style: MyTextStyle.subtitle()),
                              SizedBox(
                                height: 10,
                              ),
                              Text(
                                convertToBRL(widget.account.balance),
                                style: MyTextStyle.title(),
                              )
                            ])),
                  ),
                ),
              ),
              SizedBox(
                height: 5,
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Container(
                  child: FutureBuilder<List<StatementModel>>(
                      future: bankAccountController.fetchStatements(),
                      builder: (context, snapshot) {
                        return StatementCard(statements: statements);
                      }),
                ),
              ),
            ],
          ),
        ),
      ]),
    );
  }
}
