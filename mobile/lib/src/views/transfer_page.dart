import 'package:flutter/material.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/components/buttons/primary_button.dart';
import 'package:mobile/src/components/headers/header.dart';
import 'package:mobile/src/components/inputs/number_input.dart';
import 'package:mobile/src/components/list_views/contact_list_view.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';
import 'package:mobile/src/models/contact_model.dart';
import 'package:mobile/src/models/transfer_model.dart';
import 'package:mobile/src/repositories/bank_account_repository.dart';
import 'package:mobile/src/utils/string_util.dart';

class TransferPage extends StatefulWidget {
  TransferPage({Key key}) : super(key: key);

  @override
  _TransferPageState createState() => _TransferPageState();
}

class _TransferPageState extends State<TransferPage> {
  TransferModel transaction = TransferModel.empty();
  List<ContactModel> contacts = [];

  void fetchData() async {
    transaction = await storage.getTransfer();
    contacts = await bankAccountRepository.fetchContacts();
  }

  BankAccountRepository bankAccountRepository = new BankAccountRepository();
  TextEditingController controller = new TextEditingController();

  @override
  void initState() {
    super.initState();
    fetchData();
  }

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
              child: FutureBuilder<TransferModel>(
                future: storage.getTransfer(),
                builder: (context, snapshot) {
                  return Header(
                      title:
                          'Pra quem você quer transferir ${convertToBRL(transaction.amount)}?',
                      subtitle:
                          'Digite um pix ou o número da conta para iniciar uma nova transferência');
                },
              ),
            ),
            SizedBox(
              height: 40,
            ),
            Padding(
                padding: const EdgeInsets.symmetric(horizontal: 25.0),
                child: NumberInput(
                  controller: controller,
                  onChange: (value) {},
                  hintText: 'Pix ou número da conta',
                )),
            Column(
              children: [
                SizedBox(height: 40),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 25.0),
                  child: PrimaryButton(
                    onPress: () async {
                      String to = controller.text;
                      TransferModel transaction = await storage.getTransfer();
                      transaction.to = to;
                      transaction.toName = to;
                      storage.setTransfer(transaction);
                      Navigator.of(navigatorKey.currentContext).pushNamed("/confirm-transfer");
                    },
                    text: 'Transferir',
                  ),
                ),
              ],
            ),
            SizedBox(height: 40),
            FutureBuilder<List<ContactModel>>(
                future: bankAccountRepository.fetchContacts(),
                builder: (context, snapshot) {
                  return Visibility(
                      visible: contacts.length != 0,
                      child: Column(
                        children: [
                          Text('Todos os contatos',
                              style: MyTextStyle.subtitle()),
                          ContactListView(
                            contacts: contacts,
                          ),
                        ],
                      ));
                }),
          ],
        ),
      ),
    );
  }
}
