import 'package:flutter/material.dart';
import 'package:mobile/src/components/alerts/alert.dart';
import 'package:mobile/src/models/contact_model.dart';

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
            leading: Icon(Icons.account_circle_rounded),
            title: Text(contact.username),
            onTap: (){
              Alert.displaySimpleAlert('Clicou', 'Selecionado -> $contact');
            },
          );
        });
  }
}
