import 'package:flutter/material.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/components/buttons/primary_button.dart';
import 'package:mobile/src/components/headers/header.dart';
import 'package:mobile/src/components/inputs/number_input.dart';
import 'package:mobile/src/components/list_views/contact_list_view.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';
import 'package:mobile/src/models/transfer_model.dart';

class TransferPage extends StatefulWidget {
  TransferPage({Key key}) : super(key: key);

  @override
  _TransferPageState createState() => _TransferPageState();
}

class _TransferPageState extends State<TransferPage> {
  final List<String> contacts = ['Ray', 'João', 'Daniel Dj'];

  TransferModel transaction = TransferModel.empty();

  void getTransfer()async {
     transaction = await storage.getTransfer();
  }

  @override
  void initState() {
    getTransfer();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: SingleChildScrollView(
        child: FutureBuilder<TransferModel>(
          future: storage.getTransfer(),
          builder: (context, snapshot) {
            return
            Column(
              children: [
                SizedBox(
                  height: 40,
                ),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 25.0),
                  child: Header(
                      title:
                      'Pra quem você quer transferir R\$ ${transaction
                          .amount}?',
                      subtitle:
                      'Digite um pix ou o número da conta para iniciar uma nova transferência'),
                ),
                SizedBox(
                  height: 40,
                ),
                Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 25.0),
                    child: NumberInput(
                      controller: TextEditingController(),
                      onChange: (value) {},
                      hintText: 'Pix ou número da conta',
                    )),
                Visibility(
                    visible: contacts.length == 0,
                    child: Column(
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
                    )),
                SizedBox(height: 40),
                Visibility(
                    visible: contacts.length != 0,
                    child: Column(
                      children: [
                        Text(
                            'Todos os contatos', style: MyTextStyle.subtitle()),
                        ContactListView(
                          contacts: contacts,
                        ),
                      ],
                    )),
              ],
            );
          }),
      ),
    );
  }
}
