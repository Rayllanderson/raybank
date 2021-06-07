
import 'package:flutter/material.dart';
import 'package:Raybank/src/components/texts/styles/text_styles.dart';

class PaymentListTitle extends StatelessWidget {
  const PaymentListTitle({Key key, this.title, this.subtitle, this.icon}) : super(key: key);

  final title;
  final subtitle;
  final icon;

  @override
  Widget build(BuildContext context) {
    return        Container(
      height: 60,
      child: ListTile(
        leading: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon),
          ],
        ),
        title: Text(title),
        subtitle: Text(subtitle, style: MyTextStyle.subtitle()),
      ),
    );
  }
}
