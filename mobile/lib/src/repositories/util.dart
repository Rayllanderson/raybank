import 'package:Raybank/main.dart';

Map<String, String> getHeaders(){
  Map<String, String> headers = Map();
  headers["Content-Type"] = "application/json; charset=utf-8";
  return headers;
}

Future<Map<String, String>> getAuthHeaders() async {
  String userToken = '';
  await storage.getToken().then((value) {
    userToken = value;
  });
  Map<String, String> headers = Map();
  headers["Content-Type"] = "application/json; charset=utf-8";
  headers["Authorization"] = 'Bearer ' + userToken;
  return headers;
}
