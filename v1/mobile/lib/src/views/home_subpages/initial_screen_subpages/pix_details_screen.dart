import 'package:flutter/material.dart';
import 'package:Raybank/src/components/alerts/alert.dart';
import 'package:Raybank/src/components/buttons/pix_details_buttons.dart';
import 'package:Raybank/src/components/buttons/primary_button.dart';
import 'package:Raybank/src/components/headers/header.dart';
import 'package:Raybank/src/controllers/pix_controller.dart';
import 'package:Raybank/src/models/enums/pixType.dart';
import 'package:Raybank/src/models/pix_response.dart';
import 'package:Raybank/src/themes/themes.dart';

class PixDetails extends StatefulWidget {
  const PixDetails({Key key, this.pix, this.type}) : super(key: key);

  final PixResponse pix;
  final PixDetailsType type;

  @override
  _PixDetailsState createState() => _PixDetailsState();
}

class _PixDetailsState extends State<PixDetails> {
  final TextEditingController inputController = new TextEditingController();
  PixController controller;

  @override
  initState() {
    controller = new PixController(inputController);
    if (widget.type == PixDetailsType.EDIT) {
      inputController.text = widget.pix.pixKeys;
    }
    super.initState();
  }


  @override
  void dispose() {
     inputController.clear();
     inputController.dispose();
    super.dispose();
  }

  pixHeader() {
    if (widget.type == PixDetailsType.NEW) {
      return newPixHeader();
    } else {
      return editPixHeader();
    }
  }

  pixBody() {
    if (widget.type == PixDetailsType.NEW) {
      return newPixBody();
    } else {
      return editPixBody();
    }
  }

  newPixHeader() {
    return Header(
      title: 'Nova chave pix',
      subtitle: 'Cadastre uma digitando abaixo. '
          'Pode ser email, número de telefone, cpf ou qualquer outro dado',
      crossAxisAlignment: CrossAxisAlignment.start,
    );
  }

  editPixHeader() {
    return Header(
      title: 'O que você quer fazer com a chave "${widget.pix.pixKeys}"?',
      subtitle: 'Você pode editar ou excluir',
      crossAxisAlignment: CrossAxisAlignment.start,
    );
  }

  newPixBody() {
    return Column(
      children: [
        SizedBox(
          height: 90,
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 15),
          child: PrimaryButton(
            text: 'Cadastrar',
            onPress: () {
              controller.register();
            },
          ),
        )
      ],
    );
  }

  editPixBody() {
    return Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
      SizedBox(
        height: 165,
      ),
      Padding(
          padding: const EdgeInsets.symmetric(horizontal: 15),
          child: NormalButton(
            text: 'Editar',
            onPress: () {
              Alert.displayConfirmAlert("Atenção",
              "Você tem certeza que deseja editar a chave pix?",
              () {
              controller.edit(widget.pix.id);
              Navigator.of(context).pop();});
              },
            color: Colors.blue
          )),
      Padding(
          padding: const EdgeInsets.symmetric(horizontal: 15),
          child: NormalButton(
            text: 'Excluir',
            onPress: () {
              Alert.displayConfirmAlert("Atenção",
                  "Você tem certeza que deseja excluir a chave pix?",
                  () {
                controller.remove('${widget.pix.id}');
                Navigator.of(context).pop();});
            },
            color: Colors.red,
          ))
    ]);
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
                      height: 450,
                      child: Card(
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        child: Column(
                          children: [
                            SizedBox(
                              height: 30,
                            ),
                            Padding(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 15, vertical: 6),
                              child: pixHeader(),
                            ),
                            SizedBox(
                              height: 60,
                            ),
                            Padding(
                              padding:
                                  const EdgeInsets.symmetric(horizontal: 15),
                              child: TextField(
                                controller: inputController,
                                maxLength: 99,
                                decoration: InputDecoration(
                                  hintText: 'Digite uma chave Pix',
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(10)),
                                  labelText: 'Pix',
                                ),
                              ),
                            ),
                            pixBody()
                          ],
                        ),
                      ),
                    ))
              ],
            ),
          ),
        ),
      ]),
    );
  }
}
