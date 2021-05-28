import 'package:mobile/src/exceptions/validation_exception.dart';
import 'package:mobile/src/models/register_model.dart';
import 'package:mobile/src/utils/string_util.dart';
import 'package:mobile/src/validations/login_validation.dart';

/// Verifica se os campos de cadastro estão vazios.
///
/// Caso estejam, o método lança [ValidationException] com campos preenchidos.
void validateRegister(RegisterModel model){
  validateLogin(model.toLoginModel());
  if(isEmpty(model.name)){
    throw new ValidationException(title: 'Campos vazios', message: 'Um ou mais campos estão vazios');
  }
}