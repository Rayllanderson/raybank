import 'package:mobile/src/exceptions/validation_exception.dart';
import 'package:mobile/src/utils/string_util.dart';
import 'package:mobile/src/validations/login_validation.dart';

//receber depois um model
void validateRegister(String name, String username, String password){
  validateLogin(username, password);
  if(isEmpty(name)){
    throw new ValidationException(title: 'Campos vazios', message: 'Um ou mais campos estão vazios');
  }
}