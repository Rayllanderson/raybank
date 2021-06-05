import 'package:flutter/material.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/components/buttons/primary_button.dart';
import 'package:mobile/src/components/cards/page_card.dart';
import 'package:mobile/src/components/headers/header.dart';
import 'package:mobile/src/models/transfer_model.dart';

class ConfirmTransferPage extends StatefulWidget {
  const ConfirmTransferPage({Key key}) : super(key: key);

  @override
  _ConfirmTransferPageState createState() => _ConfirmTransferPageState();
}

class _ConfirmTransferPageState extends State<ConfirmTransferPage> {

  TransferModel transaction = TransferModel.empty();

  void fetchData() async {
    transaction = await storage.getTransfer();
  }

  @override
  void initState() {
    fetchData();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Center(
        child: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              SizedBox(
                height: 40,
              ),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 25.0),
                child: FutureBuilder<TransferModel>(
                  future: storage.getTransfer(),
                  builder: (context, snapshot) {
                    return Header(
                        title:
                        'Transferindo R\$ ${transaction.amount}',
                        subtitle:
                        'Para ${transaction.to}');
                  },
                ),
              ),
              SizedBox(
                height: 40,
              ),
              Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 40.0),
                  child: TextField(
                    decoration: InputDecoration(
                      labelText: "Escreva uma mensagem...",
                      fillColor: Colors.white,
                      focusedBorder:OutlineInputBorder(
                        borderSide: const BorderSide(color: Colors.purpleAccent, width: 2.0),
                        borderRadius: BorderRadius.circular(25.0),
                      ),
                    ),
                  )),
              Column(
                children: [
                  SizedBox(height: 40),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 25.0),
                    child: PrimaryButton(
                      onPress: () {},
                      text: 'Transferir',
                    ),
                  ),
                ],
              ),
              SizedBox(height: 40),
            ],
          ),
        ),
      ),
    );
  }
}
