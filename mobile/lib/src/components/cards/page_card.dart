import 'package:flutter/material.dart';
import 'package:Raybank/src/components/headers/header.dart';
import 'package:Raybank/src/components/inputs/text_input_masked.dart';
import 'package:Raybank/src/components/texts/styles/text_styles.dart';

class PageCard extends StatefulWidget {
  final headerTitle;
  final headerSubtitle;
  final moneyMaskedController;
  final String errorText;
  final bool Function() isErrorVisible;
  final void Function(String) inputChange;

  const PageCard(
      {Key key,
      this.headerTitle,
      this.headerSubtitle,
      this.moneyMaskedController,
      this.inputChange, this.isErrorVisible, this.errorText})
      : super(key: key);

  @override
  _PageCardState createState() => _PageCardState();
}

class _PageCardState extends State<PageCard> {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 600,
      child: Card(
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10),
        ),
        child: Column(
          children: [
            SizedBox(
              height: 80,
            ),
            Header(title: widget.headerTitle, subtitle: widget.headerSubtitle),
            SizedBox(
              height: 60,
            ),
            Padding(
                padding: const EdgeInsets.symmetric(horizontal: 25.0),
                child: TextInputMasked(
                  controller: widget.moneyMaskedController,
                  onChange: widget.inputChange,
                )
            ),
            SizedBox(height: 60),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 25.0),
              child: Visibility(
                  visible: widget.isErrorVisible(),
                  child: Text(widget.errorText, style: MyTextStyle.errorText(),
              )),
            ),
          ],
        ),
      ),
    );
  }
}
