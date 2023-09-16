package com.rayllanderson.raybank.services.statements;

import com.rayllanderson.raybank.models.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateStatementServiceFactory {
    private final List<CreateStatementService> statementServices;

    public CreateStatementService getStatementService(final Transaction transaction) {
        return statementServices.stream()
                .filter(service -> service.supports(transaction))
                .findFirst()
                .orElseThrow(CreateStatementServiceNotFoundException::new);
    }
}
