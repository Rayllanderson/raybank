package com.rayllanderson.raybank.pix.model.key;

import com.rayllanderson.raybank.utils.RegexPattern;

import java.util.UUID;

public enum PixKeyType {
    PHONE {
        @Override
        public boolean isValid(final String key) {
            return RegexPattern.PHONE_REGEX.matcher(key).matches();
        }
    }, EMAIL {
        @Override
        public boolean isValid(String key) {
            return RegexPattern.EMAIL_REGEX.matcher(key).matches();
        }
    }, RANDOM {
        @Override
        public boolean isValid(String key) {
            try {
                return UUID.fromString(key).toString() != null;
            } catch (final Exception e) {
                return false;
            }
        }
    }, CPF {
        @Override
        public boolean isValid(String key) {
            return RegexPattern.CPF_REGEX.matcher(key).matches();
        }
    };


    public abstract boolean isValid(final String key);
}
