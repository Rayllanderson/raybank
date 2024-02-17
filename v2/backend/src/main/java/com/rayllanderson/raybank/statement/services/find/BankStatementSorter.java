package com.rayllanderson.raybank.statement.services.find;

import io.github.rayexpresslibraries.ddd.domain.pagination.query.Direction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BankStatementSorter {

    public static void sortByProperty(List<BankStatementOutput> list, String property, Direction direction) {
        if (property == null || direction == null)
            return;

        Comparator<BankStatementOutput> comparator = (statement1, statement2) -> {
            Comparable<Object> value1 = (Comparable<Object>) getValueForProperty(statement1, property);
            Comparable<Object> value2 = (Comparable<Object>) getValueForProperty(statement2, property);

            boolean ascending = Direction.ASC.equals(direction);
            if (ascending) {
                return value1.compareTo(value2);
            } else {
                return value2.compareTo(value1);
            }
        };

        list.sort(comparator);
    }

    private static Comparable<?> getValueForProperty(BankStatementOutput statement, String property) {
        return switch (property) {
            case "id" -> statement.getId();
            case "moment" -> statement.getMoment();
            case "amount" -> statement.getAmount();
            case "type" -> statement.getType();
            case "method" -> statement.getMethod();
            case "credit_name" -> statement.getCreditName();
            case "debit_name" -> statement.getDebitName();
            case "financial_movement" -> statement.getFinancialMovement();
            default -> throw new IllegalArgumentException("Invalid sort bank statement property: " + property);
        };
    }
}
