package com.rayllanderson.raybank.statement.factory;

import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Debit;

interface BankStatementFinder {

    BankStatement.Credit find(Credit credit);
    BankStatement.Debit find(Debit debit);
    boolean supports(final Debit.Origin debitOrigin);
    boolean supports(final Credit.Destination destination);
}
