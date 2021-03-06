

import 'package:dio/dio.dart';
import 'package:Raybank/src/models/statement_model.dart';
import 'package:Raybank/src/repositories/util.dart';
import 'package:Raybank/src/services/api.dart';

class CreditCardRepository{

  final dio = Dio();

  Future<List<StatementModel>> fetchStatements() async {
    dio.options.headers = await getAuthHeaders();
    final response = await dio.get(creditCardUrl + "/statements");
    return List.from(response.data).map((statement) => StatementModel.fromJson(statement)).toList();
  }
}