class PixResponse {
  int id;
  String pixKeys;

  PixResponse({this.id, this.pixKeys});

  PixResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    pixKeys = json['pixKeys'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['pixKeys'] = this.pixKeys;
    return data;
  }

  Map<String, dynamic> toPutJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['key'] = this.pixKeys;
    return data;
  }
}