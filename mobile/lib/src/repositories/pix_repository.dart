

import 'package:dio/dio.dart';
import 'package:mobile/src/models/payment_model.dart';
import 'package:mobile/src/models/pix_request.dart';
import 'package:mobile/src/models/pix_response.dart';
import 'package:mobile/src/repositories/util.dart';
import 'package:mobile/src/services/api.dart';

class PaymentRepository{

  final dio = Dio();

  Future<void> register(PixRequest model) async {
    dio.options.headers = await getAuthHeaders();
    await dio.post(pixUrl, data: model.toJson());
  }

  Future<List<PixResponse>> findAll() async {
    dio.options.headers = await getAuthHeaders();
    var response = await dio.get(payCreditCardUrl);
    return List.from(response.data).map((pix) => PixResponse.fromJson(pix)).toList();
  }

  Future<void> update(PixResponse model) async {
    dio.options.headers = await getAuthHeaders();
    await dio.put(pixUrl, data: model.toPutJson());
  }

  Future<void> delete(String id) async {
    dio.options.headers = await getAuthHeaders();
    await dio.delete('$pixUrl/$id');
  }

}