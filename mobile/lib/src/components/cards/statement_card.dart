import 'package:flutter/material.dart';
import 'package:mobile/src/components/list_views/statement_list_view.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';
import 'package:mobile/src/models/statement_model.dart';

class StatementCard extends StatelessWidget {
  const StatementCard({Key key, this.statements})
      : super(key: key);

  final List<StatementModel> statements;

  List<StatementModel> getSortedList(){
    statements.sort((a, b) => a.moment.compareTo(b.moment));
    return List.of(statements.reversed);
  }
  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      height: statements.isEmpty ? 100 : 500,
      child: Card(
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10),
        ),
        child: Padding(
          padding: const EdgeInsets.only(left: 15, top: 8, bottom: 8),
          child: SizedBox(
              height: 100,
              child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text('Histórico', style: MyTextStyle.subtitle()),
                    SizedBox(
                      height: 10,
                    ),
                    Visibility(
                      visible: statements.isNotEmpty,
                      child: Expanded(
                          child: StatementList(
                        statements: getSortedList(),
                      )),
                    ),
                    Visibility(
                      visible: statements.isEmpty,
                      child: Center(
                        child: Text('Sem histórico',
                          style: MyTextStyle.subtitle(),
                        ),
                      )
                    ),
                  ]
              )
          ),
        ),
      ),
    );
  }
}
