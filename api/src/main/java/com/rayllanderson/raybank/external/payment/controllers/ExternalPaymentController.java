package com.rayllanderson.raybank.external.payment.controllers;

import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentRequest;
import com.rayllanderson.raybank.external.payment.requests.ExternalPaymentTypeDto;
import com.rayllanderson.raybank.external.payment.responses.ExternalPaymentResponse;
import com.rayllanderson.raybank.external.payment.services.ExternalCreditCardPaymentService;
import com.rayllanderson.raybank.external.payment.services.ExternalDebitCardPaymentService;
import com.rayllanderson.raybank.external.payment.services.ExternalPaymentMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/public/external/payments")
@RequiredArgsConstructor
public class ExternalPaymentController {

    private final ExternalCreditCardPaymentService creditCardPaymentService;
    private final ExternalDebitCardPaymentService debitCardPaymentService;

    @Transactional
    @PostMapping
    public ResponseEntity<ExternalPaymentResponse> pay(@Valid @RequestBody ExternalPaymentRequest request) {
        log.info("Novo pagamento recebido: {}", request);

        var response = this.getPaymentMethod(request.getPaymentMethod()).pay(request);

        log.info("Pagamento processado com sucesso: {}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private ExternalPaymentMethod getPaymentMethod(ExternalPaymentTypeDto paymentMethod) {
        switch (paymentMethod) {
            case CREDIT_CARD:
                return creditCardPaymentService;
            case DEBIT_CARD:
                return debitCardPaymentService;
            default: {
                log.error("Tipo de pagamento externo inválido: {}", paymentMethod);
                throw new RaybankExternalException.InvalidPaymentMethod("Payment type=" + paymentMethod + " is invalid");
            }
        }
    }



}
