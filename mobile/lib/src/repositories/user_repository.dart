import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:mobile/main.dart';
import 'package:mobile/src/exceptions/bad_request_exception.dart';
import 'package:mobile/src/models/api_response.dart';
import 'package:mobile/src/models/login_model.dart';
import 'package:mobile/src/models/register_model.dart';
import 'package:mobile/src/models/user_model.dart';
import 'package:mobile/src/repositories/util.dart';
import 'package:mobile/src/services/api.dart';

class UserRepository {
  final dio = Dio();

  Future<Response> register(RegisterModel userToBeRegistered) async {
    return dio.post(registerUrl, data: userToBeRegistered);
  }

  Future<Response> login(LoginModel userToLogin) async {
    return dio.post(loginUrl, data: userToLogin);
  }
}
