import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:mobile/src/models/api_response.dart';
import 'package:mobile/src/models/erro_model.dart';

ApiError catchError(Response e){
  var error = APIResponseError.fromJson(e.data);
  String message = error.getMessageError();
  return ApiError(title: "Ocorreu um erro", message: message);
}