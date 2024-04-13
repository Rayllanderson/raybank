import 'package:flutter/material.dart';

class NumberInput extends StatelessWidget {
  final controller;
  final void Function(String) onChange;
  final hintText;

  const NumberInput({Key key, this.controller, this.onChange, this.hintText})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return TextField(
      keyboardType: TextInputType.number,
      onChanged: onChange,
      controller: controller,
      decoration: InputDecoration(
        hintText: hintText,
      ),
      style: TextStyle(fontSize: 26),
    );
  }
}
