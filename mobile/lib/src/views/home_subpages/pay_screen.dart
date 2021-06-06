import 'package:flutter/material.dart';
import 'package:mobile/src/components/buttons/pay_button.dart';
import 'package:mobile/src/components/cards/page_card.dart';
import 'package:mobile/src/controllers/transfer_controller.dart';
import 'package:mobile/src/themes/themes.dart';
import 'package:mobile/src/utils/creator_util.dart';

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
              ),
              SizedBox(
                height: 20,
              ),
              PayButton(
                text: 'Pagar com crédito',
                icon: Icons.credit_card_rounded,
              ),
              SizedBox(
                height: 20,
              ),
              PayButton(
                text: 'Pagar Fatura',
                icon: Icons.analytics_sharp,
              )
            ],
          ),
        ),
      ),
    );
  }
}
