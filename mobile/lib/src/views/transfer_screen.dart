import 'package:flutter/material.dart';
import 'package:mobile/src/components/headers/header.dart';
import 'package:mobile/src/components/inputs/text_input_masked.dart';
import 'package:mobile/src/utils/mask_util.dart';

class TransferScreen extends StatefulWidget {
  const TransferScreen({Key key}) : super(key: key);

  @override
  _TransferScreenState createState() => _TransferScreenState();
}

class _TransferScreenState extends State<TransferScreen> {

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 600,
      child: Card(
        child: Column(
          children: [
            SizedBox(height: 80,),
            Header(
                title: 'Qual o valor da transferência?',
                subtitle: 'Você tem disponível R\$ 140'
            ),
            SizedBox(height: 60,),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 25.0),
              child: TextInputMasked(
                controller: moneyMaskedController,
                onChange: (value){
                  print(value);
                },
              )
            ),
          ],
        ),
      ),
    );
  }
}
