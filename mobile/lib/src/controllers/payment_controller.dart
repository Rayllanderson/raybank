import 'package:mobile/src/models/enums/payment_type.dart';
import 'package:mobile/src/models/payment_model.dart';
import 'package:mobile/src/services/payment_service.dart';
import 'package:mobile/src/utils/mask_util.dart';

class PaymentController {

  final moneyController;
  final PaymentService service = new PaymentService();
  final PaymentModel paymentModel;

  PaymentController(this.moneyController, this.paymentModel);

  void pay(){
    double valueToBePaid = double.parse(unmaskMoney(moneyController.text));
    paymentModel.amount = valueToBePaid;
    if(valueToBePaid == 0.0) return;
    switch(paymentModel.type){
      case PaymentType.BRAZILIAN_BOLETO:
        service.payBoleto(paymentModel);
        break;
      case PaymentType.CREDIT_CARD:
        service.payWithCreditCard(paymentModel);
        break;
      case PaymentType.INVOICE:
        service.payInvoice(paymentModel);
        break;
    }
  }


  bool isAmountInvalid(){
    double value = double.tryParse(unmaskMoney(moneyController.text)) ?? 0.0;
    return value > paymentModel.availableAmount;
  }
}