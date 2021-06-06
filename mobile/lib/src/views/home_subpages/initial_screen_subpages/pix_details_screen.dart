import 'package:flutter/material.dart';
import 'package:mobile/src/components/buttons/primary_button.dart';
import 'package:mobile/src/components/headers/header.dart';
import 'package:mobile/src/models/pix_response.dart';
import 'package:mobile/src/themes/themes.dart';

class PixDetails extends StatelessWidget {
  const PixDetails({Key key, this.pix}) : super(key: key);

  final PixResponse pix;


  pixHeader(){
    String type = 'new';
    if (type == 'new'){
      return newPixHeader();
    }
  }

  pixBody(){
    String type = 'new';
    if (type == 'new'){
      return newPixBody();
    }
  }


  newPixHeader(){
    return Header(
      title: 'Nova chave pix',
      subtitle: 'Cadastre uma digitando abaixo. '
          'Pode ser email, número de telefone, cpf ou qualquer outro dado',
      crossAxisAlignment: CrossAxisAlignment.start,
    );
  }

  newPixBody(){
    return Column(
      children: [
        SizedBox(height: 260,),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 15),
          child: PrimaryButton(
            text: 'Cadastrar',
            onPress: (){

            },
          ),
        )
      ],
    );
  }

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
                            padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 6),
                            child: pixHeader(),
                          ),
                          SizedBox(height: 60,),
                          Padding(
                            padding: const EdgeInsets.symmetric(horizontal: 15),
                            child: TextField(
                              maxLength: 99,
                              decoration: InputDecoration(
                                hintText: 'Digite uma chave Pix',
                                border: OutlineInputBorder(
                                    borderRadius:  BorderRadius.circular(10)
                                ),
                                  labelText: 'Pix',
                                ),
                              ),
                          ),
                          pixBody()
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
    );
  }


}
