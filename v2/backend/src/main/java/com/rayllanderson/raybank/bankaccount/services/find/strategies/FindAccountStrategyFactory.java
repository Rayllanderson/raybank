package com.rayllanderson.raybank.bankaccount.services.find.strategies;

import com.rayllanderson.raybank.bankaccount.services.find.FindAccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAccountStrategyFactory {
    private final List<FindAccountStrategy> finders;

    public FindAccountStrategy getFinderStrategy(FindAccountType findAccountType) {
        return finders.stream()
                .filter(f -> f.supports(findAccountType)).findAny()
                .orElseThrow();
    }
}
