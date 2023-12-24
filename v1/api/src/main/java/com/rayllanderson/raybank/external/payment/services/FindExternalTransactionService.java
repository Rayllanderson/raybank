package com.rayllanderson.raybank.external.payment.services;

import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.external.payment.repositories.ExternalTransactionRepository;
import com.rayllanderson.raybank.external.payment.responses.ExternalTransactionDetailsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindExternalTransactionService {

    private final ExternalTransactionRepository transactionRepository;

    public ExternalTransactionDetailsResponse findById(String id, String token) {
        log.info("Buscando Transação externa {} com token {}", id, token);

        var transaction = transactionRepository.findById(id).orElseThrow(() -> {
            log.error("Transação externa id={} não encontrada", id);
            throw new RaybankExternalException.TransactionNotFound("Transaction " + id + " not found");
        });

        log.info("Transação externa encontrada={}", transaction);

        var invalidToken = !transaction.isValidToken(token);
        if (invalidToken) {
            log.error("Erro com transação externa {}. Token não corresponde", id);
            throw new RaybankExternalException.TokenInvalid("The token " + token  +" is not valid for the transaction");
        }

        return ExternalTransactionDetailsResponse.fromModel(transaction);
    }
}
