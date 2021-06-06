import 'package:flutter/material.dart';
import 'package:mobile/src/components/list_views/statement_list_view.dart';
import 'package:mobile/src/components/texts/styles/text_styles.dart';
import 'package:mobile/src/models/statement_model.dart';

class StatementCard extends StatelessWidget {
  const StatementCard({Key key, this.statements})
      : super(key: key);

  final List<StatementModel> statements;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      height: 500,
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
                    Expanded(
                        child: StatementList(
                      statements: List.from(statements.reversed),
                    )),
                  ])),
        ),
      ),
    );
  }
}
