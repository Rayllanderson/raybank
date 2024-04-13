package com.rayllanderson.raybank.e2e.containers.postgres;

import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.contact.repository.ContactRepository;
import com.rayllanderson.raybank.e2e.HttpPeform;
import com.rayllanderson.raybank.e2e.factory.BankAccountCreator;
import com.rayllanderson.raybank.e2e.factory.PixKeyCreator;
import com.rayllanderson.raybank.e2e.helpers.AccountHelper;
import com.rayllanderson.raybank.e2e.validator.ContactValidator;
import com.rayllanderson.raybank.e2e.validator.InvoiceValidator;
import com.rayllanderson.raybank.e2e.validator.StatementValidator;
import com.rayllanderson.raybank.e2e.validator.TransactionValidator;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import com.rayllanderson.raybank.statement.repositories.BankStatementRepository;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

public abstract class E2eApiTest implements HttpPeform, StatementValidator, ContactValidator, TransactionValidator, InvoiceValidator {

    @Autowired
    private MockMvc mvc;
    @Autowired
    protected BankAccountRepository bankAccountRepository;
    @Autowired
    protected CardRepository cardRepository;
    @Autowired
    protected InvoiceRepository invoiceRepository;
    @Autowired
    protected ContactRepository contactRepository;
    @Autowired
    protected TransactionRepository transactionRepository;
    @Autowired
    protected AccountHelper accountHelper;
    @Autowired
    protected BankAccountCreator accountCreator;
    @Autowired
    protected PixKeyCreator pixKeyCreator;
    @Autowired
    protected BankStatementRepository bankStatementRepository;

    @Override
    public MockMvc mvc() {
        return mvc;
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("postgres.host", () -> PostgresContainerUtil.getPostgres().getHost());
        registry.add("postgres.port", () -> PostgresContainerUtil.getPostgres().getMappedPort(5432));
    }

    protected void deposit(String value, String accountId) {
        accountHelper.deposit(new BigDecimal(value), accountId);
    }

    protected void deposit(double value, String accountId) {
        accountHelper.deposit(new BigDecimal(value), accountId);
    }

    @Override
    public ContactRepository getContactRepository() {
        return this.contactRepository;
    }

    @Override
    public BankStatementRepository getBankSatatementRepository() {
        return this.bankStatementRepository;
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return this.transactionRepository;
    }

    @Override
    public InvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

    protected Invoice getCurrentInvoice(String cardId) {
        return invoiceRepository.findAllByCard_Id(cardId).stream().findFirst().get();
    }
}
