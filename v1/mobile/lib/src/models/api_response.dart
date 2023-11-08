class APIResponseError {
  String timestamp;
  int status;
  String title;
  String message;
  String path;
  String fields;
  String fieldsMessage;

  APIResponseError(
      {this.timestamp,
      this.status,
      this.title,
      this.message,
      this.path,
      this.fields,
      this.fieldsMessage});

  APIResponseError.fromJson(Map<String, dynamic> json) {
    timestamp = json['timestamp'];
    status = json['status'];
    title = json['title'];
    message = json['message'];
    path = json['path'];
    fields = json['fields'];
    fieldsMessage = json['fieldsMessage'];
  }

  getMessageError(){
    if(fields == null || fields.isEmpty){
      return message;
    } else {
     return fields.split(",")[0] + ": " + fieldsMessage.split(",")[0];
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['timestamp'] = this.timestamp;
    data['status'] = this.status;
    data['title'] = this.title;
    data['message'] = this.message;
    data['path'] = this.path;
    data['fields'] = this.fields;
    data['fieldsMessage'] = this.fieldsMessage;
    return data;
  }
}
