import 'dart:convert';

import 'package:mobile/src/models/transfer_model.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Storage {

   Future setToken(String token) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setString('token', token);
  }

   Future<String> getToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString('token');
  }

   Future setTransfer(TransferModel transaction) async {
     SharedPreferences prefs = await SharedPreferences.getInstance();
     await prefs.setString('transaction', json.encode(transaction));
   }

   Future<TransferModel> getTransfer() async {
     SharedPreferences prefs = await SharedPreferences.getInstance();
     return TransferModel.fromJson(json.decode(prefs.get('transaction')));
   }

   Future setAccountName(String name) async {
     SharedPreferences prefs = await SharedPreferences.getInstance();
     await prefs.setString('accountName', name);
   }

   Future<String> getAccountName() async {
     SharedPreferences prefs = await SharedPreferences.getInstance();
     return prefs.getString('accountName');
   }

   Future setAccountNumber(String number) async {
     SharedPreferences prefs = await SharedPreferences.getInstance();
     await prefs.setString('accountNumber', number);
   }

   Future<String> getAccountNumber() async {
     SharedPreferences prefs = await SharedPreferences.getInstance();
     return prefs.getString('accountNumber');
   }

}
