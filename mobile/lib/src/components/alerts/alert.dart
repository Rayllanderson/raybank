import 'package:flutter/material.dart';

class Alert {
  static void displaySimpleAlert(context, title, message) {
    showDialog(
        context: context,
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
