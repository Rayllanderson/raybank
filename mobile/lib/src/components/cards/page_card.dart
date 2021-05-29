import 'package:flutter/material.dart';
import 'package:mobile/src/components/headers/header.dart';
import 'package:mobile/src/components/inputs/text_input_masked.dart';

class PageCard extends StatefulWidget {
  final headerTitle;
  final headerSubtitle;
  final moneyMaskedController;
  final void Function(String) inputChange;

  const PageCard(
      {Key key,
      this.headerTitle,
      this.headerSubtitle,
      this.moneyMaskedController,
      this.inputChange})
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
                )),
          ],
        ),
      ),
    );
  }
}
