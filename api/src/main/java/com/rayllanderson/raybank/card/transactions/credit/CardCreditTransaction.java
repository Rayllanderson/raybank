package com.rayllanderson.raybank.transaction.models.card;

import com.rayllanderson.raybank.card.services.credit.CardCreditInput;
import com.rayllanderson.raybank.transaction.models.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CardCreditTransaction extends Transaction {

    private String cardId;
    @Embedded
    private CardCreditOrigin origin;

    @Getter
    @Setter
    @Embeddable
    public static class CardCreditOrigin {
        private String identifier;
        @Column(name = "_type")
        @Enumerated(EnumType.STRING)
        private CardCreditOriginType type;

        public static CardCreditOrigin from(CardCreditInput.CreditOrigin origin) {
            final var o = new CardCreditOrigin();
            o.identifier = origin.getIdentifier();
            o.type = o.getType();
            return o;
        }
    }

    public static CardCreditTransaction from(final CardCreditInput input) {
        return CardCreditTransaction.builder()
                .amount(input.getAmount())
                .moment(LocalDateTime.now())
                .description(input.getOrigin().getType().name())
                .cardId(input.getCardId())
                .accountId(input.getCardId())
                .origin(CardCreditOrigin.from(input.getOrigin()))
                .build();
    }
}
