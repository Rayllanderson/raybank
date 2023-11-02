package com.rayllanderson.raybank.e2e.containers.postgres;

import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.contact.repository.ContactRepository;
import com.rayllanderson.raybank.e2e.HttpPeform;
import com.rayllanderson.raybank.e2e.factory.BankAccountCreator;
import com.rayllanderson.raybank.e2e.factory.PixKeyCreator;
import com.rayllanderson.raybank.e2e.helpers.AccountHelper;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

public abstract class E2eApiTest implements HttpPeform {

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
}
