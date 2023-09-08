package com.rayllanderson.raybank.external.validator;


import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.models.BankAccount;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

@Slf4j
public class AccountExistsValidator implements ConstraintValidator<AccountExists, Object> {

    private Class<?> klass;
    private String field;

    @PersistenceContext
    private final EntityManager em;

    public AccountExistsValidator(EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(AccountExists constraintAnnotation) {
        this.klass = BankAccount.class;
        this.field = "id";
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        String jpql = "SELECT 1 FROM " + klass.getName() + " WHERE " + field + " = :field";
        List<?> result = em.createQuery(jpql).setParameter("field", object).getResultList();
        if (result.isEmpty()) {
            log.warn("Tentativa de comunicação externa não processada. Motivo: account id {} não encontrado", field);
            throw new RaybankExternalException.AccountNotFound("Account id=" + field + " not found");
        }
        return true;
    }
}
