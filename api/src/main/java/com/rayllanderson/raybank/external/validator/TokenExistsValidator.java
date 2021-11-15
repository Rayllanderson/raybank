package com.rayllanderson.raybank.external.validator;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rayllanderson.raybank.external.exceptions.RaybankExternalException;
import com.rayllanderson.raybank.external.exceptions.RaybankExternalTypeError;
import com.rayllanderson.raybank.external.token.ExternalToken;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Slf4j
public class TokenExistsValidator implements ConstraintValidator<TokenExists, Object> {

    private Class<?> klass;
    private String field;

    @PersistenceContext
    private final EntityManager em;

    public TokenExistsValidator(EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(TokenExists constraintAnnotation) {
        this.klass = ExternalToken.class;
        this.field = "token";
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        String jpql = "SELECT 1 FROM " + klass.getName() + " WHERE " + field + " = :field";
        List<?> result = em.createQuery(jpql).setParameter("field", object).getResultList();
        if (result.isEmpty()) {
            var reason = RaybankExternalTypeError.TOKEN_UNREGISTERED;
            log.warn("Tentativa de comunicação externa não processada. Motivo: {}", reason.getDescription());
            throw new RaybankExternalException(reason);
        }
        return true;
    }
}
