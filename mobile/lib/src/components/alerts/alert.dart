import 'package:flutter/material.dart';
import 'package:mobile/main.dart';

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
}
