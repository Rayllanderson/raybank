package com.rayllanderson.raybank.external.payment;

import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError.INVALID_PAYMENT_METHOD;

@Slf4j
@RestController
@RequestMapping("/api/v1/public/external/payment")
@RequiredArgsConstructor
public class ExternalPaymentController {

    private final ExternalCreditCardPaymentService creditCardPaymentService;
    private final ExternalDebitCardPaymentService debitCardPaymentService;

    @Transactional
    @PostMapping
    public ResponseEntity<Void> pay(@Valid @RequestBody ExternalPaymentRequest request) {
        log.info("Novo pagamento recebido: {}", request);

        this.getPaymentMethod(request.getPaymentMethod()).pay(request);

        log.info("Pagamento processado com sucesso: {}", request);

        return ResponseEntity.ok().build();
    }

    private ExternalPaymentMethod getPaymentMethod(ExternalPaymentType paymentMethod) {
        switch (paymentMethod) {
            case CREDIT_CARD:
                return creditCardPaymentService;
            case DEBIT_CARD:
                return debitCardPaymentService;
            default: {
                log.error("Tipo de pagamento externo inválido: {}", paymentMethod);
                throw new RaybankExternalException(INVALID_PAYMENT_METHOD, "Payment type=" + paymentMethod + " is invalid");
            }
        }
    }



}
