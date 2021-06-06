import 'package:flutter/material.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/components/cards/initial_page_card.dart';
import 'package:mobile/src/models/bank_account_model.dart';
import 'package:mobile/src/themes/themes.dart';
import 'package:mobile/src/utils/string_util.dart';

class InitialScreen extends StatefulWidget {

  final BankAccountModel accountModel;

  const InitialScreen({Key key, this.accountModel}) : super(key: key);

  @override
  _InitialScreenState createState() => _InitialScreenState();
}

class _InitialScreenState extends State<InitialScreen> {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.only(left: 15, top: 8, bottom: 8),
          child: Container(
            alignment: Alignment.topLeft,
            child: Text(
              'Olá, ' + widget.accountModel.userName ?? '',
              style: TextStyle(
                  fontWeight: FontWeight.bold,
                  color: Themes.textColor,
                  fontSize: 28),
            ),
          ),
        ),
        InitialPageCard(
            cardTap: (){print('cartão de credito');},
            title: 'Cartão de crédito',
            icon: Icon(Icons.credit_card_rounded),
            sizedBoxHeight: 5.0,
            body: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text('Fatura atual',
                    style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w300,
                        color: Colors.black)),
                SizedBox(
                  height: 3,
                ),
                Text(convertToBRL(widget.accountModel.creditCardDto.invoice),
                    style: TextStyle(
                        fontSize: 30,
                        fontWeight: FontWeight.bold,
                        color: Colors.blue)),
                SizedBox(
                  height: 3,
                ),
                Row(
                  children: [
                    Text('Limite disponível',
                        style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.w300,
                            color: Colors.black)),
                    Text(convertToBRL(widget.accountModel.creditCardDto.balance),
                        style: TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.normal,
                            color: Colors.green)),
                  ],
                )
              ],
            )),
        InitialPageCard(
          cardTap: (){
            Navigator.of(navigatorKey.currentContext).pushNamed('/balance');
          },
          title: 'Saldo disponível',
          icon: Icon(Icons.account_balance_wallet),
          cardHeight: 170.0,
          body: Text(convertToBRL(widget.accountModel.balance),
              style: TextStyle(
                  fontSize: 30,
                  fontWeight: FontWeight.bold,
                  color: Colors.black)),
        ),
        InitialPageCard(
            cardTap: (){print(' pix');},
            title: 'Pix',
            icon: Image.asset("assets/images/pix.png", width: 24),
            cardHeight: 170.0,
            body: ElevatedButton(
              child: Text(
                'Acessar minha area Pix',
                style: TextStyle(color: Themes.primaryColor),
              ),
              style: ElevatedButton.styleFrom(
                  minimumSize: Size(0, 43.0),
                  primary: Themes.textColor,
                  side: BorderSide(color: Themes.primaryColor),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20), // <-- Radius
                  ),
              ),
              onPressed: () {},
            )),
      ],
    );
  }
}
