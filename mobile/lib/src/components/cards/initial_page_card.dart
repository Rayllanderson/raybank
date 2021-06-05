import 'package:flutter/material.dart';
import 'package:mobile/src/themes/themes.dart';

class InitialPageCard extends StatefulWidget {
  final title;
  final icon;
  final body;
  final cardHeight;
  final sizedBoxHeight;
  final void Function() cardTap;

  const InitialPageCard({Key key, this.title, this.icon, this.body, this.sizedBoxHeight = 10.0, this.cardHeight = 190.0, this.cardTap})
      : super(key: key);

  @override
  _InitialPageCardState createState() => _InitialPageCardState();
}

class _InitialPageCardState extends State<InitialPageCard> {
  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      height: widget.cardHeight,
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: GestureDetector(
          onTap: widget.cardTap,
          child: Card(
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
            color: Themes.secondaryColor,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                SizedBox(
                  height: widget.sizedBoxHeight,
                ),
                Padding(
                    padding: const EdgeInsets.only(left: 15, top: 12, bottom: 8),
                    child: RichText(
                      text: TextSpan(
                        children: [
                          WidgetSpan(
                            child: widget.icon,
                          ),
                          TextSpan(
                              text: ' ${widget.title}',
                              style: TextStyle(
                                  fontSize: 18,
                                  fontWeight: FontWeight.w300,
                                  color: Colors.black)),
                        ],
                      ),
                    )),
                SizedBox(
                  height: widget.sizedBoxHeight,
                ),
                 Padding(
                  padding: const EdgeInsets.only(left: 12, top: 8, bottom: 8),
                   child: widget.body,
                 )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
