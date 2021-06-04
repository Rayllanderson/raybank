import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:mobile/src/exceptions/bad_request_exception.dart';
import 'package:mobile/src/models/api_response.dart';
import 'package:mobile/src/models/register_model.dart';
import 'package:mobile/src/repositories/util.dart';
import 'package:mobile/src/services/api.dart';

class UserRepository {
  static Future<void> register(RegisterModel userToBeRegistered) async {
    print(json.encode(userToBeRegistered.toJson()));
    print(userToBeRegistered.toJson());
    final url = Uri.parse(registerUrl);
    await http.post(url, body: json.encode(userToBeRegistered.toJson()), headers: getHeaders()).then((data) {
      bool fail = !(data.statusCode == 201);
      if (fail) {
        final jsonData = json.decode(utf8.decode(data.bodyBytes));
        var error = APIResponseError.fromJson(jsonData);
        String message = error.getMessageError();
        throw BadRequestException(
            title: "Ocorreu um erro", message: message);
      }
    });
  }
}
