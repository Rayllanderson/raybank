package com.rayllanderson.raybank.statement.services.create;

import com.rayllanderson.raybank.transaction.models.Transaction;
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
