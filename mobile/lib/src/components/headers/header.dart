import 'package:flutter/material.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';

class Header extends StatelessWidget {
  const Header({Key key, this.title, this.subtitle}) : super(key: key);

  final title;
  final subtitle;

  @override
  Widget build(BuildContext context) {
    return Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(title, style: MyTextStyle.title()),
          SizedBox(height: 4),
          Text(subtitle, style: MyTextStyle.subtitle())
        ]
    );
  }
}
