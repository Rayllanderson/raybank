package com.rayllanderson.raybank.invoice.facade;

import com.rayllanderson.raybank.invoice.services.credit.InvoiceCreditCurrentInput;
import com.rayllanderson.raybank.invoice.services.credit.InvoiceCreditCurrentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceCreditCurrentFacade {
    private final InvoiceCreditCurrentService service;

    public void process(InvoiceCreditCurrentFacadeInput input) {
        service.credit(new InvoiceCreditCurrentInput(input.getCardId(), input.getAmountToBeCredited()));
    }
}
