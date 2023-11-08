import 'package:Raybank/src/components/loaders/index.dart';
import 'package:flutter/material.dart';
import 'package:Raybank/src/components/drawer/drawer.dart';
import 'package:Raybank/src/components/headers/header.dart';
import 'package:Raybank/src/controllers/pix_controller.dart';
import 'package:Raybank/src/models/enums/pixType.dart';
import 'package:Raybank/src/models/pix_response.dart';
import 'package:Raybank/src/themes/themes.dart';
import 'package:Raybank/src/views/home_subpages/initial_screen_subpages/pix_details_screen.dart';

class PixScreen extends StatefulWidget {
  const PixScreen({Key key}) : super(key: key);

  @override
  _PixScreenState createState() => _PixScreenState();
}

class _PixScreenState extends State<PixScreen> {
  List<PixResponse> keys = List.empty();
  PixController controller;

  fetchData() async {
    keys = await controller.findAll();
  }

  refresh() async {
    var result = await controller.findAll();
    setState(() {
      keys = result;
    });
  }

  @override
  void initState() {
    controller = new PixController(null);
    fetchData();
    super.initState();
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: [
          IconButton(icon: Icon(Icons.refresh_outlined),
              onPressed: refresh
          )],
      ),
      drawer: MyDrawer(),
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
                      child: FutureBuilder<List<PixResponse>>(
                        future: controller.findAll(),
                        builder: (context, snapshot){
                          if (snapshot.connectionState == ConnectionState.waiting) {
                            return loading();
                          }
                         return Column(
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
                              Text('${keys.length} de 5 chaves', style: TextStyle(
                                fontSize: 15,
                                color: Colors.black54,
                              ),),
                              ListView.builder(
                                scrollDirection: Axis.vertical,
                                shrinkWrap: true,
                                itemCount: keys.length,
                                itemBuilder: (context, index) {
                                  var item = keys[index];
                                  return (
                                  ListTile(
                                    leading: Icon(Icons.vpn_key, size: 18,),
                                    title: Text(item.pixKeys),
                                    onTap: (){
                                      Navigator.push(context,MaterialPageRoute(builder: (context) => PixDetails(pix: item, type: PixDetailsType.EDIT,)));
                                    },
                                  )
                                  );
                                }
                              ),
                            ],
                          );
                         }
                        ,
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
          onPressed: (){
            Navigator.push(context,MaterialPageRoute(builder: (context) => PixDetails(type: PixDetailsType.NEW,)));
          },
          child: Icon(Icons.add),
        ),
      ),
    );
  }


}
