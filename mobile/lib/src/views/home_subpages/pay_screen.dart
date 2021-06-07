import 'package:flutter/material.dart';
import 'package:Raybank/src/components/buttons/pay_button.dart';
import 'package:Raybank/src/models/bank_account_model.dart';
import 'package:Raybank/src/models/enums/payment_type.dart';
import 'package:Raybank/src/models/payment_model.dart';
import 'package:Raybank/src/views/payment_page.dart';

class PayScreen extends StatefulWidget {

  const PayScreen({Key key, this.account}) : super(key: key);
  final BankAccountModel account;
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
                  Navigator.push(context,MaterialPageRoute(builder: (context) => PaymentPage(
                    paymentModel: PaymentModel(PaymentType.BRAZILIAN_BOLETO,
                        widget.account.balance),
                  )));
                },
              ),
              SizedBox(
                height: 20,
              ),
              PayButton(
                text: 'Pagar com crédito',
                icon: Icons.credit_card_rounded,
                onPress: (){
                  Navigator.push(context,MaterialPageRoute(builder: (context) => PaymentPage(
                    paymentModel: PaymentModel(PaymentType.CREDIT_CARD,
                        widget.account.creditCardDto.balance),)));
                },
              ),
              SizedBox(
                height: 20,
              ),
              PayButton(
                text: 'Pagar Fatura',
                icon: Icons.analytics_sharp,
                onPress: (){
                  Navigator.push(context,MaterialPageRoute(builder: (context) => PaymentPage(
                    paymentModel: PaymentModel(PaymentType.INVOICE,
                        widget.account.creditCardDto.invoice),)));
                },
              )
            ],
          ),
        ),
      ),
    );
  }
}
