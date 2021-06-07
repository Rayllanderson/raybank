import 'package:flutter/material.dart';
import 'package:Raybank/main.dart';

class Alert {
  static void displaySimpleAlert(title, message) {
    showDialog(
        context: navigatorKey.currentContext,
        builder: (context) {
          return AlertDialog(
              title: Text(title),
              content: Text(message),
              actions: [
                TextButton(
                  child: Text("Ok"),
                  onPressed: () {
                    Navigator.of(context).pop();
                  },
                ),
              ]);
        });
  }

  static Future<void> displayConfirmAlert(title, message, void Function() onPressed) async {
    return showDialog<void>(
      context: navigatorKey.currentContext,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(title),
          content: SingleChildScrollView(
            child: Column(
              children: <Widget>[
                Text(message),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Cancelar'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('Confirmar'),
              onPressed: onPressed,
            ),
          ],
        );
      },
    );
  }
}
