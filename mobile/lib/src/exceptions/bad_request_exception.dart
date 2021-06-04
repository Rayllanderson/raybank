class BadRequestException implements Exception{
  String title;
  String message;

  BadRequestException({this.title, this.message});
}
