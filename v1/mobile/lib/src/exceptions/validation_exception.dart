class ValidationException implements Exception{
  String title;
  String message;

  ValidationException({this.title, this.message});
}
