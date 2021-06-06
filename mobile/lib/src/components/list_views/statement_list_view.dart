import 'package:flutter/material.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';
import 'package:mobile/src/models/statement_model.dart';

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
          return ListTile(
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
          );
        });
  }
}
