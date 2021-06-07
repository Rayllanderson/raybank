import 'package:flutter/material.dart';
import 'package:flutter_masked_text/flutter_masked_text.dart';
import 'package:Raybank/src/components/buttons/primary_button.dart';
import 'package:Raybank/src/components/inputs/text_input_masked.dart';
import 'package:Raybank/src/components/list_views/payment_list_title.dart';
import 'package:Raybank/src/components/texts/styles/text_styles.dart';
import 'package:Raybank/src/controllers/payment_controller.dart';
import 'package:Raybank/src/models/payment_model.dart';
import 'package:Raybank/src/themes/themes.dart';
import 'package:Raybank/src/utils/mask_util.dart';
import 'package:Raybank/src/utils/string_util.dart';

class PaymentPage extends StatefulWidget {
  const PaymentPage({Key key, this.paymentModel}) : super(key: key);

  final PaymentModel paymentModel;

  @override
  _PaymentPageState createState() => _PaymentPageState();
}

class _PaymentPageState extends State<PaymentPage> {
  final moneyMaskedController = MoneyMaskedTextController(
      decimalSeparator: ',', thousandSeparator: '.', leftSymbol: 'R\$ ');

  bool isErrorVisible = false;

  PaymentController paymentController;

  @override
  void initState() {
    this.paymentController = new PaymentController(moneyMaskedController, widget.paymentModel);
    super.initState();
  }

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
                             onChange: (value){
                               setState(() {
                                 isErrorVisible = paymentController.isAmountInvalid();
                               });
                             },
                           )
                       ),
                       SizedBox(height: 30),
                       Padding(
                         padding: const EdgeInsets.symmetric(horizontal: 25.0),
                         child: Visibility(
                             visible: isErrorVisible,
                             child: Text('A quantia é maior que o ${widget.paymentModel.getTitle().toString().toLowerCase()}', style: MyTextStyle.errorText(),
                             )),
                       ),
                       PaymentListTitle(
                         title: 'Tipo de pagamento',
                         subtitle: widget.paymentModel.getPaymentType(),
                         icon: Icons.assignment_late,
                       ),
                       PaymentListTitle(
                         title: widget.paymentModel.getTitle(),
                         subtitle: convertToBRL(widget.paymentModel.availableAmount),
                         icon: Icons.account_balance_wallet,
                       ),

                       SizedBox(height: 120),
                       Padding(
                         padding: const EdgeInsets.all(8.0),
                         child: PrimaryButton(
                           text: "Pagar",
                           onPress: isErrorVisible ? null : (){
                             paymentController.pay();
                           },
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
