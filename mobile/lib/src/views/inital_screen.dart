import 'package:flutter/material.dart';
import 'package:mobile/src/components/cards/initial_page_card.dart';
import 'package:mobile/src/themes/themes.dart';

class InitialScreen extends StatelessWidget {
  const InitialScreen({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.only(left: 15, top: 8, bottom: 8),
          child: Container(
            alignment: Alignment.topLeft,
            child: Text(
              'Olá, João!',
              style: TextStyle(
                  fontWeight: FontWeight.bold,
                  color: Themes.textColor,
                  fontSize: 28),
            ),
          ),
        ),

        InitialPageCard(
          title: 'Cartão de crédito',
          icon: Icon(Icons.credit_card_rounded),
          height: 5.0,
          body: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text('Fatura atual',
                  style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w300,
                      color: Colors.black)
              ),
              SizedBox(height: 2,),
              Text('R\$ 266,54',
                  style: TextStyle(
                      fontSize: 30,
                      fontWeight: FontWeight.bold,
                      color: Colors.blue)
              ),
              SizedBox(height: 2,),
              Row(
                children: [
                  Text('Limite disponível',
                      style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w300,
                          color: Colors.black
                      )
                  ),
                  Text(' R\$ 547,57',
                      style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.normal,
                          color: Colors.green
                      )
                  ),
                ],
              )
            ],
          )
        ),

        InitialPageCard(
          title: 'Saldo disponível',
          icon: Icon(Icons.account_balance_wallet),
          height: 10.0,
          body: Text('R\$ 50,75',
              style: TextStyle(
                  fontSize: 35,
                  fontWeight: FontWeight.bold,
                  color: Colors.black)),
        ),
        InitialPageCard(
            title: 'Pix',
            icon: Image.asset("assets/images/pix.png", width: 24),
            height: 10.0,
            body: ElevatedButton(
                  child: Text(
                    'Acessar minha area Pix',
                    style: TextStyle(color: Themes.primaryColor),
                  ),
                  style: ElevatedButton.styleFrom(
                      minimumSize: Size(0, 43.0),
                      primary: Themes.textColor,
                      side: BorderSide(color: Themes.primaryColor)),
                  onPressed: () {},
                )
        ),
      ],
    );
  }
}
