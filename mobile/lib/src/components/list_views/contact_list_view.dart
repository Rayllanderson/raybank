import 'package:flutter/material.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/components/alerts/alert.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';
import 'package:mobile/src/models/contact_model.dart';
import 'package:mobile/src/models/transfer_model.dart';

class ContactListView extends StatelessWidget {
  final List<ContactModel> contacts;

  const ContactListView({Key key, this.contacts}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        scrollDirection: Axis.vertical,
        shrinkWrap: true,
        itemCount: contacts != null ? contacts.length : 0,
        itemBuilder: (context, index) {
          var contact = contacts[index];
          return ListTile(
            leading: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Icon(Icons.account_circle_rounded, size: 28, color: Colors.purpleAccent,),
              ],
            ),

            title: Text(contact.username, style: MyTextStyle.listTitle(),),
            subtitle: Text('Nº Conta ${contact.accountNumber}', style: MyTextStyle.listSubtitle()),
            onTap: () async {
              TransferModel transaction = await storage.getTransfer();
              transaction.to = contact.accountNumber.toString();
              transaction.toName = contact.name;
              storage.setTransfer(transaction);
              Navigator.of(navigatorKey.currentContext).pushNamed("/confirm-transfer");
            },
          );
        });
  }
}
