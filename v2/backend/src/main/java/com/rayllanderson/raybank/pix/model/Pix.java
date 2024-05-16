package com.rayllanderson.raybank.pix.model;

import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.model.qrcode.PixQrCode;
import com.rayllanderson.raybank.pix.util.E2EIdGenerator;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.DEBIT_SAME_ACCOUNT;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Pix {

    @Id
    private String id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PixType type;

    @Embedded
    @AttributeOverride(name = "key", column = @Column(name = "debit_key"))
    @AttributeOverride(name = "name", column = @Column(name = "debit_name"))
    @AttributeOverride(name = "type", column = @Column(name = "debit_type"))
    @AttributeOverride(name = "createdAt", column = @Column(name = "debit_created_at"))
    @AttributeOverride(name = "accountId", column = @Column(name = "debit_account_id"))
    private PixKeyData debit;

    @Embedded
    @AttributeOverride(name = "key", column = @Column(name = "credit_key"))
    @AttributeOverride(name = "name", column = @Column(name = "credit_name"))
    @AttributeOverride(name = "type", column = @Column(name = "credit_type"))
    @AttributeOverride(name = "createdAt", column = @Column(name = "credit_created_at"))
    @AttributeOverride(name = "accountId", column = @Column(name = "credit_account_id"))
    private PixKeyData credit;

    private String message;

    @OneToOne
    private PixQrCode qrCode;

    private LocalDateTime occurredOn;

    public String getCreditName() {
        return credit.getName();
    }

    public String getDebitName() {
        return debit.getName();
    }

    public String getCreditAccountId() {
        return this.credit.getAccountId();
    }

    public String getDebitAccountId() {
        return this.debit.getAccountId();
    }

    public static Pix newTransfer(PixKey debit, PixKey credit, BigDecimal amount, String message) {
        final LocalDateTime occurred = LocalDateTime.now();

        if (debit.sameAccount(credit))
            throw UnprocessableEntityException.with(DEBIT_SAME_ACCOUNT, "Não é possível transferir PIX para si mesmo");

        return new Pix(E2EIdGenerator.generateE2E(occurred), amount, PixType.TRANSFER, PixKeyData.from(debit), PixKeyData.from(credit), message, null, occurred);
    }

    public static Pix newPayment(PixQrCode qrCode, PixKey debit) {
        final LocalDateTime occurred = LocalDateTime.now();
        qrCode.setPaid();
        return new Pix(E2EIdGenerator.generateE2E(occurred), qrCode.getAmount(), PixType.PAYMENT, PixKeyData.from(debit), qrCode.getCredit(), qrCode.getDescription(), qrCode, occurred);
    }
}
