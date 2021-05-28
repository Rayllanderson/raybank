import 'package:mobile/src/exceptions/validation_exception.dart';
import 'package:mobile/src/validations/string_validation.dart';

//receber depois um model
void validateLogin(String username, String password){
  if(isEmpty(username) || isEmpty(password)){
    throw new ValidationException(title: 'Campos vazios', message: 'Um ou mais campos estão vazios');
  }
}