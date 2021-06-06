import 'package:flutter/cupertino.dart';
import 'package:mobile/src/models/pix_request.dart';
import 'package:mobile/src/models/pix_response.dart';
import 'package:mobile/src/services/pix_service.dart';

class PixController {

  final PixService service = new PixService();
  final TextEditingController inputController;

  PixController(this.inputController);

  Future<List<PixResponse>> findAll() async {
    return await service.findAll();
  }

  Future<void> register() async {
    String key = inputController.text;
    var toBeSaved = PixRequest(key: key);
    service.register(toBeSaved);
  }

  Future<void> edit(int id) async {
    String key = inputController.text;
    var toBeUpdated = PixResponse(id: id, pixKeys: key);
    service.update(toBeUpdated);
  }

  Future<void> remove(String id) async {
    service.delete(id);
  }


}
