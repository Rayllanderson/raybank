import 'package:flutter/material.dart';
import 'package:Raybank/src/components/texts/styles/text_styles.dart';

class Header extends StatelessWidget {
  const Header({Key key, this.title, this.subtitle, this.crossAxisAlignment = CrossAxisAlignment.center}) : super(key: key);

  final title;
  final subtitle;
  final crossAxisAlignment;

  @override
  Widget build(BuildContext context) {
    return Column(
        crossAxisAlignment: crossAxisAlignment,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(title, style: MyTextStyle.title()),
          SizedBox(height: 4),
          Text(subtitle, style: MyTextStyle.subtitle())
        ]
    );
  }
}
