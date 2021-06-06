import 'package:flutter/material.dart';
import 'package:mobile/src/themes/themes.dart';

class PayButton extends StatelessWidget {
  const PayButton({Key key, this.text, this.icon, this.onPress}) : super(key: key);

  final text;
  final icon;
  final onPress;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20.0),
      child: ElevatedButton(onPressed: onPress,
        child: Container(
          height: 90,
          width: 155,
          child: Padding(
            padding: const EdgeInsets.only(top: 20),
            child: Column(
              children: [
                Icon(icon, size: 26,),
                SizedBox(height: 5,),
                Text(text, style: TextStyle(
                    fontSize: 18
                ),),
              ],
            ),
          ),
        ),
        style: ElevatedButton.styleFrom(
          primary: Themes.primaryColor,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10), // <-- Radius
          ),
        ),
      ),
    );
  }
}
