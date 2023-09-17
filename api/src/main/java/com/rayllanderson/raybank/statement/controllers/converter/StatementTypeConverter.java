package com.rayllanderson.raybank.statement.controllers.converter;

import com.rayllanderson.raybank.statement.controllers.StatementTypeParam;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatementTypeConverter implements Converter<String, StatementTypeParam> {

    @Override
    public StatementTypeParam convert(final String source) {
        try {
            return StatementTypeParam.valueOf(source.toUpperCase());
        } catch (final Exception e) {
            return StatementTypeParam.ALL;
        }
    }
}
