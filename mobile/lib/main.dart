import 'package:flutter/material.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:mobile/src/utils/storage_util.dart';
import 'package:mobile/src/views/confirm_transfer_page.dart';
import 'package:mobile/src/views/home_page.dart';
import 'package:mobile/src/views/home_subpages/initial_screen_subpages/balace_screen.dart';
import 'package:mobile/src/views/login_page.dart';
import 'package:mobile/src/views/register_page.dart';
import 'package:mobile/src/views/transfer_page.dart';

final navigatorKey = GlobalKey<NavigatorState>();
final Storage storage = new Storage();

void main() async {
  initializeDateFormatting();
  String initialPage = '/';
  WidgetsFlutterBinding.ensureInitialized();
  await storage.getToken().then((value) {
    if (value.isNotEmpty) initialPage = '/home';
  });

  runApp(MaterialApp(
    title: 'Raybank',
    navigatorKey: navigatorKey,
    theme: ThemeData(
      primarySwatch: Colors.purple,
    ),
    initialRoute: initialPage,
    routes: {
      '/': (context) => LoginPage(),
      '/register': (context) => RegisterPage(),
      '/home': (context) => HomePage(),
      '/transfer': (context) => TransferPage(),
      '/confirm-transfer': (context) => ConfirmTransferPage(),
      '/pix': (context) => BalanceScreen(),
    },
  ));
}
