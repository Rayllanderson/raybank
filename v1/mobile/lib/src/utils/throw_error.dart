
import 'package:dio/dio.dart';
import 'package:Raybank/src/models/api_response.dart';
import 'package:Raybank/src/models/erro_model.dart';

ApiError catchError(DioError e){
  if (e.response != null) {
    var error = APIResponseError.fromJson(e.response.data);
    String message = error.getMessageError();
    return ApiError(title: "Ocorreu um erro", message: message);
  }
  return  ApiError(title: "Ocorreu um erro", message: 'Ocorreu um erro no servidor');
}