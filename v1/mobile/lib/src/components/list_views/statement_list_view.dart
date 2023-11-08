import 'package:flutter/material.dart';
import 'package:Raybank/src/components/alerts/alert.dart';
import 'package:Raybank/src/models/statement_model.dart';

class StatementList extends StatelessWidget {
  const StatementList({Key key, this.statements}) : super(key: key);

  final List<StatementModel> statements;

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        scrollDirection: Axis.vertical,
        itemCount: statements.length,
        shrinkWrap: true,
        itemBuilder: (context, index) {
          var statement = statements[index];
          return Column(
            children: [
              ListTile(
                onTap: (){
                  Alert.displaySimpleAlert('${statement.getTitle()}', '${statement.getMessage()}');
                },
                title: Text('${statement.getTitle()}', style: TextStyle(
                    fontSize: 18
                )),
                subtitle: Text('${statement.getSubtitle()}', style: TextStyle(
                  fontSize: 17
                )),
                leading: Icon(
                  statement.getIcon(),
                  size: 24,
                  color: statement.getIconColor(),
                ),
                trailing: Text(statement.getMoment()),
              ),
              Divider(
                thickness: 0.5,
              )
            ],
          );
        });
  }
}
