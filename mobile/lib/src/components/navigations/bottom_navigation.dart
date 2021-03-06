import 'package:flutter/material.dart';
import 'package:Raybank/src/themes/themes.dart';

class BottomNavigator extends StatelessWidget {
  final selectedIndex;
  final void Function(int index) onItemTapped;

  const BottomNavigator({Key key, this.selectedIndex, this.onItemTapped}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      currentIndex: selectedIndex,
      backgroundColor: Themes.defaultColor,
      selectedItemColor: Themes.secondaryColor,
      unselectedItemColor: Themes.secondaryColor.withAlpha(150),
      type: BottomNavigationBarType.fixed,
      onTap: onItemTapped,
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          icon: Icon(Icons.home),
          label: 'Início',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.qr_code),
          label: 'Pagar',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.api),
          label: 'Transferir',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.attach_money),
          label: 'Depositar',
        ),
      ],
    );
  }
  }