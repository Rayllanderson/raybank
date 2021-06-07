import 'package:flutter/material.dart';
import 'package:Raybank/src/components/alerts/alert.dart';
import 'package:Raybank/src/components/cards/statement_card.dart';
import 'package:Raybank/src/models/bank_account_model.dart';
import 'package:Raybank/src/models/enums/payment_type.dart';
import 'package:Raybank/src/models/payment_model.dart';
import 'package:Raybank/src/models/statement_model.dart';
import 'package:Raybank/src/repositories/credit_card_repository.dart';
import 'package:Raybank/src/themes/themes.dart';
import 'package:Raybank/src/utils/string_util.dart';
import 'package:Raybank/src/views/payment_page.dart';

class CreditCardScreen extends StatefulWidget {
  const CreditCardScreen({Key key, this.creditCard}) : super(key: key);

  final CreditCardDto creditCard;

  @override
  _CreditCardScreenState createState() => _CreditCardScreenState();
}

class _CreditCardScreenState extends State<CreditCardScreen> {
  fetchData() async {
    statements = await creditCardRepository.fetchStatements();
  }

  CreditCardRepository creditCardRepository = new CreditCardRepository();
  List<StatementModel> statements = List.empty();

  @override
  void initState() {
    fetchData();
    super.initState();
  }

  payInvoice(){
    bool hasInvoice = widget.creditCard.invoice != 0;
    if (hasInvoice){
      Navigator.push(context,MaterialPageRoute(builder: (context) => PaymentPage(
        paymentModel: PaymentModel(PaymentType.INVOICE,
            widget.creditCard.invoice),)));
    } else {
      Alert.displaySimpleAlert("", "Não há faturas no momento");
    }
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
                padding: const EdgeInsets.all(8.0),
                child: Container(
                  width: double.infinity,
                  height: 180,
                  child: Card(
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Padding(
                        padding:
                            const EdgeInsets.only(left: 15, top: 20, bottom: 6),
                        child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text('Fatura atual',
                                  style: TextStyle(
                                      fontSize: 20, color: Colors.blue)),
                              SizedBox(
                                height: 5,
                              ),
                              Text(
                                convertToBRL(widget.creditCard.invoice),
                                style:
                                    TextStyle(fontSize: 28, color: Colors.blue),
                              ),
                              SizedBox(
                                height: 5,
                              ),
                              Row(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text('Limite disponível ',
                                        style: TextStyle(fontSize: 18)),
                                    Text(
                                      convertToBRL(widget.creditCard.balance),
                                      style: TextStyle(
                                          fontSize: 18,
                                          color: Colors.green,
                                          fontWeight: FontWeight.bold),
                                    )
                                  ]),
                              SizedBox(
                                height: 10,
                              ),
                              ElevatedButton(
                                child: Text(
                                  'Pagar Fatura',
                                  style: TextStyle(color: Themes.primaryColor),
                                ),
                                style: ElevatedButton.styleFrom(
                                  primary: Themes.textColor,
                                  side: BorderSide(color: Themes.primaryColor),
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(5), // <-- Radius
                                  ),
                                ),
                                onPressed: (){
                                  payInvoice();
                                },
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
                      future: creditCardRepository.fetchStatements(),
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
