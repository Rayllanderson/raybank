import 'package:flutter/material.dart';
import 'package:flutter_masked_text/flutter_masked_text.dart';
import 'package:mobile/src/components/buttons/primary_button.dart';
import 'package:mobile/src/components/cards/page_card.dart';
import 'package:mobile/src/components/inputs/text_input_masked.dart';
import 'package:mobile/src/components/list_views/payment_list_title.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';
import 'package:mobile/src/themes/themes.dart';

class PaymentPage extends StatefulWidget {
  const PaymentPage({Key key, this.paymentType}) : super(key: key);

  final paymentType;

  @override
  _PaymentPageState createState() => _PaymentPageState();
}

class _PaymentPageState extends State<PaymentPage> {
  final moneyMaskedController = MoneyMaskedTextController(
      decimalSeparator: ',', thousandSeparator: '.', leftSymbol: 'R\$ ');

  bool isErrorVisible = false;
  String errorText = '';
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Raybank'),
      ),
      body: Stack( children: [
        Container(color: Themes.primaryColor),
        Center(
          child: SingleChildScrollView(
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                 height: 600,
                 child: Card(
                   shape: RoundedRectangleBorder(
                     borderRadius: BorderRadius.circular(10),
                   ),
                   child: Column(
                     children: [
                       SizedBox(
                         height: 80,
                       ),
                       Text('Quanto quer pagar?', style: MyTextStyle.title()),
                       SizedBox(
                         height: 40,
                       ),
                       Padding(
                           padding: const EdgeInsets.symmetric(horizontal: 25.0),
                           child: TextInputMasked(
                             controller: moneyMaskedController,
                             onChange: (value){},
                           )
                       ),
                       SizedBox(height: 30),
                       Padding(
                         padding: const EdgeInsets.symmetric(horizontal: 25.0),
                         child: Visibility(
                             visible: isErrorVisible,
                             child: Text(errorText, style: MyTextStyle.errorText(),
                             )),
                       ),
                       PaymentListTitle(
                         title: 'Tipo de pagamento',
                         subtitle: widget.paymentType,
                         icon: Icons.assignment_late,
                       ),
                       PaymentListTitle(
                         title: 'Saldo disponível',
                         subtitle: 'R\$ 200',
                         icon: Icons.account_balance_wallet,
                       ),

                       SizedBox(height: 130),
                       Padding(
                         padding: const EdgeInsets.all(8.0),
                         child: PrimaryButton(
                           text: "Pagar",
                           onPress: (){},
                         ),
                       ),
                     ],
                   ),
                 ),
               ),
            ),
          ),
        ),
      ],
      ),
    );
  }
}
