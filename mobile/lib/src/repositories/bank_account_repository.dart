import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:mobile/src/exceptions/bad_request_exception.dart';
import 'package:mobile/src/models/api_response.dart';
import 'package:mobile/src/models/bank_account_model.dart';
import 'package:mobile/src/repositories/util.dart';
import 'package:mobile/src/services/api.dart';

class BankAccountRepository{

  Future<BankAccountModel> fetchBankAccount() async {
    final url = Uri.parse(bankAccountUrl);
    var headers = Map<String, String>();
    await getAuthHeaders().then((value) {
      headers = value;
    });
    Map<String, dynamic> jsonData = Map();
    await http.get(url, headers: headers).then((data) {
      bool fail = !(data.statusCode == 200);
      jsonData = json.decode(utf8.decode(data.bodyBytes));
      if (fail) {
        var error = APIResponseError.fromJson(jsonData);
        String message = error.getMessageError();
        throw BadRequestException(
            title: "Ocorreu um erro", message: message);
      }
    });
    return BankAccountModel.fromJson(jsonData);
  }
}