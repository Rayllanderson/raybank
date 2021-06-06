import 'package:flutter/material.dart';
import 'package:mobile/src/components/buttons/pay_button.dart';
import 'package:mobile/src/views/payment_page.dart';

class PayScreen extends StatefulWidget {

  const PayScreen({Key key}) : super(key: key);

  @override
  _PayScreenState createState() => _PayScreenState();
}

class _PayScreenState extends State<PayScreen> {

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 600,
      child: Card(
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10),
        ),
        child: Center(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text('Escolha o que deseja pagar', style: TextStyle(
                fontSize: 25,
                fontWeight: FontWeight.w500
              ),),
              SizedBox(
                height: 20,
              ),
              PayButton(
                text: 'Pagar um boleto',
                icon: Icons.qr_code,
                onPress: (){
                  Navigator.push(context,MaterialPageRoute(builder: (context) => PaymentPage(paymentType: "Boleto")));
                },
              ),
              SizedBox(
                height: 20,
              ),
              PayButton(
                text: 'Pagar com crédito',
                icon: Icons.credit_card_rounded,
                onPress: (){
                  Navigator.push(context,MaterialPageRoute(builder: (context) => PaymentPage(paymentType: "Cartão de crédito")));
                },
              ),
              SizedBox(
                height: 20,
              ),
              PayButton(
                text: 'Pagar Fatura',
                icon: Icons.analytics_sharp,
                onPress: (){
                  Navigator.push(context,MaterialPageRoute(builder: (context) => PaymentPage(paymentType: "Fatura")));
                },
              )
            ],
          ),
        ),
      ),
    );
  }
}
