package com.rayllanderson.raybank.boleto.services.find;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.boleto.controllers.find.BoletoDetailsResponse;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.invoice.models.Invoice;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FindBoletoMapper {

    @Mapping(target = "institutionIssuing", expression = " java(new BoletoDetailsOutput.InstitutionIssuing())")
    BoletoDetailsOutput from(Boleto boleto, Object beneficiaryData);
    BoletoDetailsOutput.BoletoData mapFrom(Boleto boleto);
    List<BoletoDetailsOutput.BoletoData> mapFrom(List<Boleto> boletos);

    @AfterMapping
    default void afterMapping(@MappingTarget BoletoDetailsOutput boletoDetailsOutput, Boleto boleto, Object beneficiaryData) {
        boletoDetailsOutput.setBoleto(mapFrom(boleto));

        if (boleto.getBeneficiary().getType().equals(BeneficiaryType.ACCOUNT)) {
            boletoDetailsOutput.getBeneficiary().setAccount(mapAccount((BankAccount) beneficiaryData));
        } else if (boleto.getBeneficiary().getType().equals(BeneficiaryType.INVOICE)) {
            boletoDetailsOutput.getBeneficiary().setInvoice(mapInvoice((Invoice) beneficiaryData));
        }
    }

    BoletoDetailsOutput.Invoice mapInvoice(Invoice invoice);

    @Mapping(target = "name", source = "user.name")
    BoletoDetailsOutput.Account mapAccount(BankAccount bankAccount);

    BoletoDetailsResponse from(BoletoDetailsOutput boleto);
    List<BoletoDetailsResponse.BoletoResponse> from(List<BoletoDetailsOutput.BoletoData> boletos);
}
