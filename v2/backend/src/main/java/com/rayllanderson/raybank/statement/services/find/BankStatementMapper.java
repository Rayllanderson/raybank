package com.rayllanderson.raybank.statement.services.find;

import com.rayllanderson.raybank.statement.controllers.BankStatementDetailsResponse;
import com.rayllanderson.raybank.statement.controllers.BankStatementResponse;
import com.rayllanderson.raybank.statement.models.BankStatement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankStatementMapper {
    BankStatementOutput from(BankStatement statament);
    List<BankStatementOutput> from(List<BankStatement> stataments);

    @Mapping(target = "creditName", source = "credit.name")
    @Mapping(target = "debitName", source = "debit.name")
    @Mapping(target = "installments", source = "installmentPlan.installments")
    BankStatementResponse from(BankStatementOutput statament);
    List<BankStatementResponse> toResponse(List<BankStatementOutput> stataments);

    BankStatementDetailsResponse toResponse(BankStatementOutput statament);
}
