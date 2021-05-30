import 'package:flutter/material.dart';
import 'package:mobile/src/components/buttons/primary_button.dart';
import 'package:mobile/src/components/headers/header.dart';
import 'package:mobile/src/components/inputs/number_input.dart';
import 'package:mobile/src/components/list_views/contact_list_view.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';

class TransferPage extends StatelessWidget {
  TransferPage({Key key}) : super(key: key);

  final List<String> contacts = ['Ray', 'João', 'Daniel Dj'];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(
              height: 40,
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 25.0),
              child: Header(
                  title: 'Pra quem você quer transferir R\$ 150,15?',
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
                  onChange: (value) {
                  },
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
                )
            ),
            SizedBox(height: 40),
            Visibility(
                visible: contacts.length != 0,
                child: Column(
                  children: [
                    Text('Todos os contatos', style: MyTextStyle.subtitle()),
                    ContactListView(contacts: contacts,),
                  ],
                )),
          ],
        ),
      ),
    );
  }
}
