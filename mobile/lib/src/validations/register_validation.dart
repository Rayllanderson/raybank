import 'package:mobile/src/exceptions/validation_exception.dart';
import 'package:mobile/src/models/login_model.dart';
import 'package:mobile/src/utils/string_util.dart';
import 'package:mobile/src/validations/login_validation.dart';

/// Verifica se os campos de cadastro estão vazios.
///
/// Caso estejam, o método lança [ValidationException] com campos preenchidos.
void validateRegister(String name, String username, String password){
  validateLogin(LoginModel(username, password));
  if(isEmpty(name)){
    throw new ValidationException(title: 'Campos vazios', message: 'Um ou mais campos estão vazios');
  }
}