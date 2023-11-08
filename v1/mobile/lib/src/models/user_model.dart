class UserModel {
  String username;
  String name;
  String token;

  UserModel({this.username, this.name, this.token});

  UserModel.fromJson(Map<String, dynamic> json) {
    username = json['username'];
    name = json['name'];
    token = json['token'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['username'] = this.username;
    data['name'] = this.name;
    data['token'] = this.token;
    return data;
  }
}