import 'package:flutter/material.dart';

class TextInputMasked extends StatelessWidget {

  final controller;
  final void Function(String) onChange;

  const TextInputMasked({Key key, this.controller, this.onChange}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return TextField(
        keyboardType: TextInputType.number,
        onChanged: onChange,
        controller: controller,
        decoration: InputDecoration(
          hintText: 'R\$ 0,00',
        ),
      style: TextStyle(
        fontSize: 28
      ),
    );
  }
}
