import 'package:flutter/material.dart';
import 'package:mobile/src/components/headers/header.dart';
import 'package:mobile/src/models/pix_response.dart';
import 'package:mobile/src/themes/themes.dart';
import 'package:mobile/src/views/home_subpages/initial_screen_subpages/pix_details_screen.dart';

class PixScreen extends StatefulWidget {
  const PixScreen({Key key}) : super(key: key);

  @override
  _PixScreenState createState() => _PixScreenState();
}

class _PixScreenState extends State<PixScreen> {
  List<PixResponse> items = [PixResponse(id: 2, pixKeys: 'keys1'), PixResponse(id: 3, pixKeys: 'key@fowjwfw')];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Stack(children: [
        Container(color: Themes.primaryColor),
        Center(
          child: SingleChildScrollView(
            child: Column(
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Container(
                    width: double.infinity,
                    height: 650,
                    child: Card(
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10),
                      ),
                      child: Column(
                        children: [
                          SizedBox(height: 30,),
                          Padding(
                            padding: const EdgeInsets.only(left: 15, bottom: 6),
                            child: Header(
                              title: 'Minhas chaves',
                              subtitle: 'Gerencie suas chaves para receber transferências pelo Pix',
                              crossAxisAlignment: CrossAxisAlignment.start,
                            ),
                          ),
                          SizedBox(height: 40,),
                          Text('${items.length} de 5 chaves', style: TextStyle(
                            fontSize: 15,
                            color: Colors.black54,
                          ),),
                          ListView.builder(
                            scrollDirection: Axis.vertical,
                            shrinkWrap: true,
                            itemCount: items.length,
                            itemBuilder: (context, index) {
                              var item = items[index];
                              return (
                              ListTile(
                                leading: Icon(Icons.vpn_key, size: 18,),
                                title: Text(item.pixKeys),
                                onTap: (){
                                  Navigator.push(context,MaterialPageRoute(builder: (context) => PixDetails(pix: item,)));
                                },
                              )
                              );
                            }
                          ),
                        ],
                      ),
                    ),
                  )
                )
              ],
            ),
          ),
        ),
      ]),
      floatingActionButton: Padding(
        padding: const EdgeInsets.only(bottom: 40, right: 10),
        child: FloatingActionButton(
          onPressed: (){},
          child: Icon(Icons.add),
        ),
      ),
    );
  }


}
