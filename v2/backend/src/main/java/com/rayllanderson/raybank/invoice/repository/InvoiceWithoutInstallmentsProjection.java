package com.rayllanderson.raybank.invoice.repository;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceWithoutInstallmentsProjection {
    private String id;
    private LocalDate dueDate;
    private LocalDate originalDueDate;
    private LocalDate closingDate;
    private InvoiceStatus status;
    private Card card;
}
