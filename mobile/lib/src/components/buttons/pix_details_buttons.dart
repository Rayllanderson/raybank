import 'package:flutter/material.dart';

class NormalButton extends StatelessWidget {
  const NormalButton({Key key, this.text, this.onPress, this.color})
      : super(key: key);

  final text;
  final onPress;
  final color;

  @override
  Widget build(BuildContext context) {
    return Container(
        height: 50,
        width: 100,
        child: ElevatedButton(
            child: Text(
              text,
              style: TextStyle(fontSize: 18),
            ),
            style: ElevatedButton.styleFrom(
              primary: color,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10), // <-- Radius
              ),
            ),
            onPressed: onPress));
  }
}
