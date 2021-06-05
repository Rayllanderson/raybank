import 'package:flutter/material.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';

class StatementList extends StatelessWidget {
  const StatementList({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        scrollDirection: Axis.vertical,
        itemCount: 20,
        shrinkWrap: true,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text('Transferência recebida', style: MyTextStyle.listTitle()),
            leading: Icon(Icons.attach_money, size: 28, color: Colors.green,),
            trailing: Icon(Icons.attach_money, size: 28, color: Colors.green,),
          );
        });
  }
}
